package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.BookCopy;
import com.my_library.model.CopyStatus;

import java.sql.Connection;
import java.util.Optional;

public interface BookCopyDAO extends GenericDAO<BookCopy, Long> {

    Optional<BookCopy> findById(Long id, Connection conn) throws DaoException;

    int countAvailableCopiesByBookId(Long bookId) throws DaoException;

    void updateStatus(Long id, CopyStatus status, Connection conn) throws DaoException;

    Optional<BookCopy> findFirstAvailableCopyByBookId(Long bookId, Connection conn) throws DaoException;

}
