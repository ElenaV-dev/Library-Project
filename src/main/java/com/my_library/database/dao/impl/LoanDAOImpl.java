package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.LoanDAO;
import com.my_library.exception.DaoException;
import com.my_library.model.Loan;
import com.my_library.util.constants.ErrorConstants;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LoanDAOImpl implements LoanDAO {

    private final ConnectionPool connectionPool;

    private static final String SELECT_LOAN_BY_ID = "SELECT * FROM loans WHERE id = ?";
    private static final String SELECT_ALL_LOANS = "SELECT * FROM loans";
    private static final String ADD_LOAN = "INSERT INTO loans (book_copy_id, user_id, loan_date, return_date)" +
            " VALUES (?, ?, ?, ?)";
    private static final String UPDATE_LOAN = "UPDATE loans SET book_copy_id = ?, user_id = ?, " +
            "loan_date = ?, return_date = ? WHERE id = ?";
    private static final String DELETE_LOAN = "DELETE FROM loans WHERE id = ?";

    public LoanDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Loan> findById(Long id) throws DaoException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_LOAN_BY_ID)) {
                stmt.setLong(1, id);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    if (resultSet.next()) {
                        return Optional.of(mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error finding loan by id", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findAll() throws DaoException {

        Connection connection = null;
        List<Loan> listLoans = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_LOANS);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    listLoans.add(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error finding all loans", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listLoans;
    }

    @Override
    public void save(Loan loan) throws DaoException {

        if (loan == null) {
            throw new IllegalArgumentException(ErrorConstants.LOAN_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(ADD_LOAN, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, loan.getBookCopyId());
                stmt.setLong(2, loan.getUserId());
                stmt.setObject(3, loan.getLoanDate(), Types.DATE);
                stmt.setObject(4, loan.getReturnDate(), Types.DATE);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_CREATE, "loan", "rows affected");
                    throw new DaoException(error);
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        loan.setId(generatedKeys.getLong(1));
                    } else {
                        String error = String.format(ErrorConstants.FAILED_TO_CREATE, "loan", "ID obtained");
                        throw new DaoException(error);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error saving loan", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public void update(Loan loan) throws DaoException {

        if (loan == null) {
            throw new IllegalArgumentException(ErrorConstants.LOAN_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_LOAN)) {
                stmt.setLong(1, loan.getBookCopyId());
                stmt.setLong(2, loan.getUserId());
                stmt.setObject(3, loan.getLoanDate(), Types.DATE);
                stmt.setObject(4, loan.getReturnDate(), Types.DATE);
                stmt.setLong(5, loan.getId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_UPDATE, "loan", "rows affected");
                    throw new DaoException(error);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error updating loan", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(Long id) throws DaoException {

        if (id == null) {
            throw new IllegalArgumentException(ErrorConstants.LOAN_ID_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_LOAN)) {
                stmt.setLong(1, id);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_DELETE, "loan", "rows affected");
                    throw new DaoException(error);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error deleting loan", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public List<Loan> findByBookCopyId(Long bookCopyId) throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public List<Loan> findByUserId(Long userId) throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public List<Loan> findByLoanDate(Date loanDate) throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public List<Loan> findByLoanDateBetween(Date start, Date end) throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public List<Loan> findByReturnDateIsNull() throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    private Loan mapRow(ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();
        loan.setId(resultSet.getLong("id"));
        loan.setBookCopyId(resultSet.getLong("book_copy_id"));
        loan.setUserId(resultSet.getLong("user_id"));
        loan.setLoanDate(resultSet.getObject("loan_date", LocalDate.class));
        loan.setReturnDate(resultSet.getObject("return_date", LocalDate.class));
        return loan;
    }
}
