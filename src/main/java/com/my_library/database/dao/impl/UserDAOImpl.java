package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.UserDAO;
import com.my_library.model.User;
import com.my_library.model.UserRole;
import com.my_library.util.constants.ErrorConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool;

    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String ADD_USER = "INSERT INTO users (last_name, first_name, role, iin, address, phone)" +
            " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET last_name = ?, first_name = ?, role = ?, " +
            "iin = ?, address = ?, phone = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public UserDAOImpl(ConnectionPool connectionPool) {

        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_USER_BY_ID)) {
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
    public List<User> findAll() throws SQLException {

        Connection connection = null;
        List<User> listUsers = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USERS);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    listUsers.add(mapRow(resultSet));
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listUsers;
    }

    @Override
    public void save(User user) throws SQLException {

        if (user == null) {
            throw new IllegalArgumentException(ErrorConstants.USER_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getLastName());
                stmt.setString(2, user.getFirstName());

                if (user.getRole() != null) {
                    stmt.setString(3, user.getRole().name());
                } else {
                    stmt.setNull(3, Types.VARCHAR);
                }

                stmt.setString(4, user.getIin());
                stmt.setString(5, user.getAddress());
                stmt.setString(6, user.getPhone());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_CREATE, "user", "rows affected");
                    throw new SQLException(error);
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    } else {
                        String error = String.format(ErrorConstants.FAILED_TO_CREATE, "user", "ID obtained");
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
    public void update(User user) throws SQLException {

        if (user == null) {
            throw new IllegalArgumentException(ErrorConstants.USER_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_USER)) {
                stmt.setString(1, user.getLastName());
                stmt.setString(2, user.getFirstName());

                if (user.getRole() != null) {
                    stmt.setString(3, user.getRole().name());
                } else {
                    stmt.setNull(3, Types.VARCHAR);
                }

                stmt.setString(4, user.getIin());
                stmt.setString(5, user.getAddress());
                stmt.setString(6, user.getPhone());
                stmt.setLong(7, user.getId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_UPDATE, "user", "rows affected");
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
            throw new IllegalArgumentException(ErrorConstants.USER_ID_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_USER)) {
                stmt.setLong(1, id);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_DELETE, "user", "rows affected");
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
    public List<User> findByLastName(String lastName) {
        return List.of();
    }

    @Override
    public List<User> findByLastNameAndFirstName(String lastName, String firstName) {
        return List.of();
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return List.of();
    }

    @Override
    public User findByIin(String iin) {
        return null;
    }

    @Override
    public User findByPhone(String phone) {
        return null;
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLastName(resultSet.getString("last_name"));
        user.setFirstName(resultSet.getString("first_name"));

        String role = resultSet.getString("role");

        if (role != null) {
            user.setRole(UserRole.valueOf(role.toUpperCase()));
        } else {
            user.setRole(null);
        }

        user.setIin(resultSet.getString("iin"));
        user.setAddress(resultSet.getString("address"));
        user.setPhone(resultSet.getString("phone"));
        return user;
    }
}
