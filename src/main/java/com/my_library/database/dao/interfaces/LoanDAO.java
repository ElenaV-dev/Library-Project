package com.my_library.database.dao.interfaces;

import com.my_library.model.Loan;

import java.util.Date;
import java.util.List;

public interface LoanDAO extends GenericDAO<Loan, Long> {

    List<Loan> findByBookCopyId(Long bookCopyId);

    List<Loan> findByUserId(Long userId);

    List<Loan> findByLoanDate(Date loanDate);

    List<Loan> findByLoanDateBetween(Date start, Date end);

    List<Loan> findByReturnDateIsNull();

}
