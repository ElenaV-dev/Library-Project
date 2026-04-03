package com.my_library.service.impl;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.LoanDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.Loan;
import com.my_library.service.interfaces.LoanService;
import com.my_library.validator.LoanValidator;

import java.util.List;
import java.util.Optional;

public class LoanServiceImpl implements LoanService {

    private final LoanDAO loanDAO = FactoryDAO.getInstance().getLoanDAO();

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
}
