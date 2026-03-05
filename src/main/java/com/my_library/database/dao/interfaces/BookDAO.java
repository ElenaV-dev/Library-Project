package com.my_library.database.dao.interfaces;

import com.my_library.model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDAO extends GenericDAO<Book, Long> {

    Optional<Book> findByIsbn(String isbn) throws SQLException;

    List<Book> findByTitle(String title) throws SQLException;

    List<Book> findByAuthorId(UUID authorId) throws SQLException;

    void addAuthorToBook(Long bookId, UUID authorId) throws SQLException;

    void removeAuthorFromBook(Long bookId, UUID authorId) throws SQLException;
}
