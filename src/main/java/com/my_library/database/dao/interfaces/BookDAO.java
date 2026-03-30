package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDAO extends GenericDAO<Book, Long> {

    Optional<Book> findByIsbn(String isbn) throws DaoException;

    List<Book> findByTitle(String title) throws DaoException;

    List<Book> findByAuthorId(UUID authorId) throws DaoException;

    void addAuthorToBook(Long bookId, UUID authorId) throws DaoException;

    void removeAuthorFromBook(Long bookId, UUID authorId) throws DaoException;

    boolean isExistByIsbn(String isbn) throws DaoException;

    boolean isExistById(Long id) throws DaoException;
}
