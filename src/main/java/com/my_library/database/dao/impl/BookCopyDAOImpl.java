package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.BookCopyDAO;
import com.my_library.exception.DaoException;
import com.my_library.model.BookCopy;
import com.my_library.model.CopyStatus;
import com.my_library.util.constants.ErrorConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookCopyDAOImpl implements BookCopyDAO {

    private final ConnectionPool connectionPool;

    private static final String SELECT_BOOK_COPY_BY_ID = "SELECT * FROM book_copies WHERE id = ?";
    private static final String SELECT_ALL_BOOK_COPY = "SELECT * FROM book_copies";
    private static final String ADD_BOOK_COPY = "INSERT INTO book_copies (book_id, inventory_number, status)" +
            " VALUES (?, ?, ?)";
    private static final String UPDATE_BOOK_COPY = "UPDATE book_copies SET book_id = ?, inventory_number = ?, " +
            "status = ? WHERE id = ?";
    private static final String DELETE_BOOK_COPY = "DELETE FROM book_copies WHERE id = ?";

    public BookCopyDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<BookCopy> findById(Long id) throws DaoException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_BOOK_COPY_BY_ID)) {
                stmt.setLong(1, id);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    if (resultSet.next()) {
                        return Optional.of(mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error finding book copy by id", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<BookCopy> findAll() throws DaoException {

        Connection connection = null;
        List<BookCopy> listBookCopy = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_BOOK_COPY);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    listBookCopy.add(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error finding all book copies", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listBookCopy;
    }

    @Override
    public void save(BookCopy bookCopy) throws DaoException {

        if (bookCopy == null) {
            throw new IllegalArgumentException(ErrorConstants.BOOK_COPY_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(ADD_BOOK_COPY, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, bookCopy.getBookId());
                stmt.setInt(2, bookCopy.getInventoryNumber());

                if (bookCopy.getStatus() != null) {
                    stmt.setString(3, bookCopy.getStatus().name());
                } else {
                    stmt.setNull(3, Types.VARCHAR);
                }

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_CREATE, "bookCopy", "rows affected");
                    throw new DaoException(error);
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bookCopy.setId(generatedKeys.getLong(1));
                    } else {
                        String error = String.format(ErrorConstants.FAILED_TO_CREATE, "bookCopy", "ID obtained");
                        throw new DaoException(error);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error saving book copy", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }

    }

    @Override
    public void update(BookCopy bookCopy) throws DaoException {

        if (bookCopy == null) {
            throw new IllegalArgumentException(ErrorConstants.BOOK_COPY_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_BOOK_COPY)) {
                stmt.setLong(1, bookCopy.getBookId());
                stmt.setInt(2, bookCopy.getInventoryNumber());

                if (bookCopy.getStatus() != null) {
                    stmt.setString(3, bookCopy.getStatus().name());
                } else {
                    stmt.setNull(3, Types.VARCHAR);
                }

                stmt.setLong(4, bookCopy.getId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_UPDATE, "bookCopy", "rows affected");
                    throw new DaoException(error);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error updating book copy", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }

    }

    @Override
    public void deleteById(Long id) throws DaoException {

        if (id == null) {
            throw new IllegalArgumentException(ErrorConstants.BOOK_COPY_ID_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_BOOK_COPY)) {
                stmt.setLong(1, id);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_DELETE, "bookCopy", "rows affected");
                    throw new DaoException(error);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error deleting book copy", e);
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public List<BookCopy> findByBookId(Long bookId) throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public List<BookCopy> findByInventoryNumber(Integer inventoryNumber) throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public List<BookCopy> findByStatus(CopyStatus status) throws DaoException {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    private BookCopy mapRow(ResultSet resultSet) throws SQLException {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(resultSet.getLong("id"));
        bookCopy.setBookId(resultSet.getLong("book_id"));
        bookCopy.setInventoryNumber(resultSet.getInt("inventory_number"));
        String status = resultSet.getString("status");

        if (status != null) {
            bookCopy.setStatus(CopyStatus.valueOf(status.toUpperCase()));
        } else {
            bookCopy.setStatus(null);
        }
        return bookCopy;
    }
}
