package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.AuthorDAO;
import com.my_library.model.Author;
import com.my_library.util.constants.ErrorConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthorDAOImpl implements AuthorDAO {

    private final ConnectionPool connectionPool;

    private static final String SELECT_AUTHOR_BY_ID = "SELECT * FROM authors WHERE id = ?";
    private static final String SELECT_ALL_AUTHORS = "SELECT * FROM authors";
    private static final String ADD_AUTHOR = "INSERT INTO authors (id, last_name, first_name, life_years)" +
            " VALUES (?, ?, ?, ?)";
    private static final String UPDATE_AUTHOR = "UPDATE authors SET last_name = ?, first_name = ?, " +
            "life_years = ? WHERE id = ?";
    private static final String DELETE_AUTHOR = "DELETE FROM authors WHERE id = ?";
    private static final String SELECT_ALL_AUTHORS_BY_LAST_NAME = "SELECT * FROM authors " +
            " WHERE LOWER(last_name) = LOWER(?)";
    private static final String SELECT_ALL_AUTHORS_BY_LAST_NAME_AND_FIRST_NAME =
            "SELECT * FROM authors " +
            " WHERE LOWER(last_name) = LOWER(?) AND LOWER(first_name) = LOWER(?)";

    public AuthorDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Author> findById(UUID uuid) throws SQLException {
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_AUTHOR_BY_ID)) {
                stmt.setObject(1, uuid);

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
    public List<Author> findAll() throws SQLException {

        Connection connection = null;
        List<Author> listAuthors = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_AUTHORS);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    listAuthors.add(mapRow(resultSet));
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listAuthors;
    }

    @Override
    public void save(Author author) throws SQLException {

        if (author == null) {
            throw new IllegalArgumentException(ErrorConstants.AUTHOR_NULL);
        }

        if (author.getUuid() == null) {
            author.setUuid(UUID.randomUUID());
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(ADD_AUTHOR)) {
                stmt.setObject(1, author.getUuid());
                stmt.setString(2, author.getLastName());
                stmt.setString(3, author.getFirstName());
                stmt.setString(4, author.getLifeYears());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_CREATE, "author", "rows affected");
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
    public void update(Author author) throws SQLException {

        if (author == null) {
            throw new IllegalArgumentException(ErrorConstants.AUTHOR_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_AUTHOR)) {
                stmt.setString(1, author.getLastName());
                stmt.setString(2, author.getFirstName());
                stmt.setString(3, author.getLifeYears());
                stmt.setObject(4, author.getUuid());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_UPDATE, "author", "rows affected");
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
    public void deleteById(UUID uuid) throws SQLException {
        if (uuid == null) {
            throw new IllegalArgumentException(ErrorConstants.AUTHOR_ID_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_AUTHOR)) {
                stmt.setObject(1, uuid);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_DELETE, "author", "rows affected");
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
    public List<Author> findByLastName(String lastName) throws SQLException {

        Connection connection = null;
        List<Author> listAuthors = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_AUTHORS_BY_LAST_NAME)) {
                stmt.setString(1, lastName);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    while (resultSet.next()) {
                        listAuthors.add(mapRow(resultSet));
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listAuthors;
    }

    @Override
    public List<Author> findByLastNameAndFirstName(String lastName, String firstName) throws SQLException {
        Connection connection = null;
        List<Author> listAuthors = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_AUTHORS_BY_LAST_NAME_AND_FIRST_NAME)) {
                stmt.setString(1, lastName);
                stmt.setString(2, firstName);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    while (resultSet.next()) {
                        listAuthors.add(mapRow(resultSet));
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listAuthors;
    }

    private Author mapRow(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setUuid(resultSet.getObject("id", UUID.class));
        author.setLastName(resultSet.getString("last_name"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLifeYears(resultSet.getString("life_years"));
        return author;
    }
}
