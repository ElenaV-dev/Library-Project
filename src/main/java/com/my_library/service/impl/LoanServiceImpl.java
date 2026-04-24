package com.my_library.service.impl;

import com.my_library.controller.impl.BookControllerImpl;
import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.BookCopyDAO;
import com.my_library.database.dao.interfaces.LoanDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.BookCopy;
import com.my_library.model.CopyStatus;
import com.my_library.model.Loan;
import com.my_library.service.interfaces.LoanService;
import com.my_library.validator.LoanValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanServiceImpl implements LoanService {

    private static final Logger LOGGER = LogManager.getLogger(LoanServiceImpl.class);
    private final LoanDAO loanDAO = FactoryDAO.getInstance().getLoanDAO();
    private final BookCopyDAO bookCopyDAO = FactoryDAO.getInstance().getBookCopyDAO();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public Optional<Loan> findById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid loan id");
        }

        try {
            return loanDAO.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find loan", e);
        }
    }

    @Override
    public List<Loan> findAll() throws ServiceException {

        try {
            return loanDAO.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find all loans", e);
        }
    }

    @Override
    public void save(Loan loan) throws ServiceException {

        try {
            LoanValidator.validate(loan);
            loanDAO.save(loan);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to save loan", e);
        }
    }

    @Override
    public void update(Loan loan) throws ServiceException {

        if (loan.getId() == null || loan.getId() <= 0) {
            throw new ServiceException("Invalid loan id");
        }

        try {
            LoanValidator.validate(loan);

            Optional<Loan> existing = loanDAO.findById(loan.getId());

            if (existing.isEmpty()) {
                throw new ServiceException("Loan not found");
            }

            loanDAO.update(loan);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update loan", e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid loan id");
        }

        try {
            loanDAO.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete loan", e);
        }
    }

    @Override
    public void issueLoan(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid loan id");
        }

        Connection conn = null;

        try {
            conn = connectionPool.takeConnection();
            conn.setAutoCommit(false);

            Optional<Loan> loanOpt = loanDAO.findById(id, conn);

            if (loanOpt.isEmpty()) {
                throw new ServiceException("Loan not found");
            }

            Loan loan = loanOpt.get();

            Long bookCopyId = loan.getBookCopyId();

            Optional<BookCopy> bookCopyOpt = bookCopyDAO.findById(bookCopyId, conn);

            if (bookCopyOpt.isEmpty()) {
                throw new ServiceException("Book copy not found");
            }

            BookCopy bookCopy = bookCopyOpt.get();

            if (bookCopy.getStatus() != CopyStatus.AVAILABLE) {
                throw new ServiceException("Book copy is not available");
            }

            bookCopyDAO.updateStatus(bookCopyId, CopyStatus.BORROWED, conn);
            loanDAO.deleteById(id, conn);
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    LOGGER.error("Rollback failed", ex);
                }
            }
            throw new ServiceException("Issue loan failed", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    LOGGER.error("Failed to reset autoCommit", e);
                }
                connectionPool.returnConnection(conn);
            }
        }
    }

    @Override
    public void requestBook(Long bookId, Long userId) throws ServiceException {

        if (bookId == null || bookId <= 0) {
            throw new ServiceException("Invalid book id");
        }

        if (userId == null || userId <= 0) {
            throw new ServiceException("Invalid user id");
        }

        Connection conn = null;

        try {
            conn = connectionPool.takeConnection();

           Optional<BookCopy> bookCopyOpt = bookCopyDAO.findFirstAvailableCopyByBookId(bookId, conn);

            if (bookCopyOpt.isEmpty()) {
                throw new ServiceException("Book copy not found");
            }

            BookCopy bookCopy = bookCopyOpt.get();

            Loan loan = new Loan();
            loan.setBookCopyId(bookCopy.getId());
            loan.setUserId(userId);
            loan.setLoanDate(LocalDate.now());
            loanDAO.save(loan, conn);

        } catch (DaoException e) {
            throw new ServiceException("Failed to request book", e);
        } finally {
            if (conn != null) {
                connectionPool.returnConnection(conn);
            }
        }
    }
}

