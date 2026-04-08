package com.my_library.database.connection;

import com.my_library.util.constants.ErrorConstants;
import com.my_library.util.constants.MessageConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue;

    private String url;
    private String user;
    private String password;
    private int poolSize;


    private ConnectionPool() {
        loadProperties();
        initPool();
    }

    private void loadProperties() {
        Properties properties = new Properties();

        try (InputStream input = ConnectionPool.class.getClassLoader()
                    .getResourceAsStream("ConnectionPool.properties")) {

            if (input == null) {
                LOGGER.error("Error finding properties");
                throw new RuntimeException("Cannot find ConnectionPool.properties in classpath");
            }

            properties.load(input);
        } catch (IOException e) {
            LOGGER.error("Error loading properties", e);
            throw new RuntimeException("Failed to load ConnectionPool.properties", e);
        }

        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
        poolSize = Integer.parseInt(properties.getProperty("db.poolSize", "5"));

        connectionQueue = new ArrayBlockingQueue<>(poolSize);

        try {
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error finding database driver", e);
            throw new RuntimeException("Database driver not found",e);
        }
    }

    private void initPool() {
        try (Connection testConnection = DriverManager.getConnection(url, user, password)) {
            LOGGER.info(MessageConstants.TEST_CONNECTION_SUCCESSFUL);
        } catch (SQLException e) {
            LOGGER.error(ErrorConstants.FAILED_TO_CONNECT_TO_THE_DATABASE, e);
            throw new RuntimeException(ErrorConstants.ERROR_CONNECTING_TO_THE_DATABASE, e);
        }

        Connection connection;

        while (connectionQueue.size() < poolSize) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                connectionQueue.put(connection);
            } catch (InterruptedException | SQLException e) {
                LOGGER.error(ErrorConstants.ERROR_CREATING_CONNECTION, e);
                throw new RuntimeException(ErrorConstants.FAILED_TO_FILL_CONNECTION_POOL, e);
            }
        }
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;

        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new ConnectionPool();
                    localInstance = instance;
                }
            }
        }
        return localInstance;
    }

    public synchronized Connection takeConnection() {
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error(ErrorConstants.CONNECTION_WAS_INTERRUPTED, e);
            throw new RuntimeException(ErrorConstants.CONNECTION_FAILED, e);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        if (connection != null && connectionQueue.size() < poolSize) {
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error(ErrorConstants.CONNECTION_WAS_INTERRUPTED, e);
                throw new RuntimeException(ErrorConstants.CONNECTION_FAILED, e);
            }
        }
    }
}

