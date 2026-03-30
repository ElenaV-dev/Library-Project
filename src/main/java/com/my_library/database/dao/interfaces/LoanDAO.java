package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.Loan;

import java.util.Date;
import java.util.List;

public interface LoanDAO extends GenericDAO<Loan, Long> {

    List<Loan> findByBookCopyId(Long bookCopyId) throws DaoException;

    List<Loan> findByUserId(Long userId) throws DaoException;

    List<Loan> findByLoanDate(Date loanDate) throws DaoException;

    List<Loan> findByLoanDateBetween(Date start, Date end) throws DaoException;

    List<Loan> findByReturnDateIsNull() throws DaoException;

}
