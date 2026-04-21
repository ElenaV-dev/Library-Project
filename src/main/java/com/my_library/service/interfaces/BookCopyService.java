package com.my_library.service.interfaces;

import com.my_library.exception.ServiceException;
import com.my_library.model.BookCopy;

public interface BookCopyService extends GenericService<BookCopy, Long> {

    int countAvailableCopiesByBookId(Long bookId) throws ServiceException;
}
