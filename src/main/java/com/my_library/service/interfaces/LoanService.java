package com.my_library.service.interfaces;

import com.my_library.exception.ServiceException;
import com.my_library.model.Loan;

public interface LoanService extends GenericService<Loan, Long> {

    void issueLoan(Long id) throws ServiceException;

    void requestBook(Long bookId, Long userId) throws ServiceException;
}
