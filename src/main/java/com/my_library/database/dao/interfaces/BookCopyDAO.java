package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.BookCopy;
import com.my_library.model.CopyStatus;

import java.util.List;

public interface BookCopyDAO extends GenericDAO<BookCopy, Long> {

    List<BookCopy> findByBookId(Long bookId) throws DaoException;

    List<BookCopy> findByInventoryNumber(Integer inventoryNumber) throws DaoException;

    List<BookCopy> findByStatus(CopyStatus status) throws DaoException;

    int countAvailableCopiesByBookId(Long bookId) throws DaoException;

}
