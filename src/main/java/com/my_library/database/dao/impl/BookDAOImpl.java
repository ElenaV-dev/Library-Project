package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.BookDAO;
import com.my_library.model.Book;
import com.my_library.util.constants.ErrorConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookDAOImpl implements BookDAO {

    private final ConnectionPool connectionPool;

    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
    private static final String ADD_BOOK = "INSERT INTO books (title, year, isbn, publisher)" +
            " VALUES (?, ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, year = ?, " +
            "isbn = ?, publisher = ? WHERE id = ?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";
    private static final String SELECT_BOOK_BY_ISBN = "SELECT * FROM books WHERE isbn = ?";
    private static final String SELECT_ALL_BOOKS_BY_TITLE = "SELECT * FROM books WHERE title ILIKE ?";
    private static final String SELECT_ALL_BOOKS_BY_AUTHOR = "SELECT b.* FROM books b " +
            " JOIN book_authors ba ON b.id = ba.book_id " +
            " WHERE ba.author_id = ?";
    private static final String ADD_AUTHOR_TO_BOOK = "INSERT INTO book_authors (book_id, author_id)" +
            " VALUES (?, ?)";
    private static final String DELETE_AUTHOR_FROM_BOOK = "DELETE FROM book_authors " +
            " WHERE book_id = ? AND author_id = ?";


    public BookDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Book> findById(Long id) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
                stmt.setLong(1, id);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    if (resultSet.next()) {
                        return Optional.of(mapRow(resultSet));
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return Optional.empty();
    }


    @Override
    public List<Book> findAll() throws SQLException {

        Connection connection = null;
        List<Book> listBooks = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_BOOKS);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    listBooks.add(mapRow(resultSet));
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listBooks;
    }

    @Override
    public void save(Book book) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(ADD_BOOK, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, book.getTitle());
                stmt.setInt(2, book.getYear());
                stmt.setString(3, book.getIsbn());
                stmt.setString(4, book.getPublisher());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_CREATE, "book", "rows affected");
                    throw new SQLException(error);
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getLong(1));
                    } else {
                        String error = String.format(ErrorConstants.FAILED_TO_CREATE, "book", "ID obtained");
                        throw new SQLException(error);
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public void update(Book book) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_BOOK)) {
                stmt.setString(1, book.getTitle());
                stmt.setInt(2, book.getYear());
                stmt.setString(3, book.getIsbn());
                stmt.setString(4, book.getPublisher());
                stmt.setLong(5, book.getId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_UPDATE, "book", "rows affected");
                    throw new SQLException(error);
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {

        if (id == null) {
            throw new IllegalArgumentException(ErrorConstants.BOOK_ID_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_BOOK)) {
                stmt.setLong(1, id);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_DELETE, "book", "rows affected");
                    throw new SQLException(error);
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) throws SQLException {
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_BOOK_BY_ISBN)) {
                stmt.setString(1, isbn);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    if (resultSet.next()) {
                        return Optional.of(mapRow(resultSet));
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findByTitle(String title) throws SQLException {

        Connection connection = null;
        List<Book> listBooks = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_BOOKS_BY_TITLE)) {
                stmt.setString(1, "%" + title + "%");

                try (ResultSet resultSet = stmt.executeQuery()) {

                    while (resultSet.next()) {
                        listBooks.add(mapRow(resultSet));
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listBooks;
    }

    @Override
    public List<Book> findByAuthorId(UUID authorId) throws SQLException {

        Connection connection = null;
        List<Book> listBooks = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_BOOKS_BY_AUTHOR)) {
                stmt.setObject(1, authorId);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    while (resultSet.next()) {
                        listBooks.add(mapRow(resultSet));
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listBooks;
    }

    @Override
    public void addAuthorToBook(Long bookId, UUID authorId) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(ADD_AUTHOR_TO_BOOK)) {
                stmt.setLong(1, bookId);
                stmt.setObject(2, authorId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_ADD, "author to book", "rows affected");
                    throw new SQLException(error);
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public void removeAuthorFromBook(Long bookId, UUID authorId) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_AUTHOR_FROM_BOOK)) {
                stmt.setLong(1, bookId);
                stmt.setObject(2, authorId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_DELETE, "author from book", "rows affected");
                    throw new SQLException(error);
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public boolean isExist(String isbn) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_BOOK_BY_ISBN)) {
                stmt.setString(1, isbn);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return false;
    }

    private Book mapRow(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setYear(resultSet.getInt("year"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPublisher(resultSet.getString("publisher"));
        return book;
    }
}
