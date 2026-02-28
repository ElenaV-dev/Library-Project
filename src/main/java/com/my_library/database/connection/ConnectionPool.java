package com.my_library.database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private String url;
    private String user;
    private String password;
    private ResourceBundle properties = ResourceBundle.getBundle("ConnectionPool");
    private final int POOL_SIZE = Integer.parseInt(properties.getString("db.poolSize"));
    private BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);

    private ConnectionPool() {
        init();
    }

    private void init() {
        this.url = properties.getString("db.url");
        this.user = properties.getString("db.user");
        this.password = properties.getString("db.password");
        initPoolData();
    }

    private void initPoolData() {
        try (Connection testConnection = DriverManager.getConnection(url, user, password)) {
            LOGGER.info("Test connection successful");
        } catch (SQLException e) {
            LOGGER.error("Failed to connect to the database. Check your connection parameters", e);
            throw new RuntimeException("Error connecting to the database", e);
        }

        Connection connection;

        while (connectionQueue.size() < POOL_SIZE) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                connectionQueue.put(connection);
            } catch (InterruptedException | SQLException e) {
                LOGGER.error("Error creating connection", e);
                throw new RuntimeException("Failed to fill connection pool", e);
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
            LOGGER.error("Thread was interrupted", e);
            throw new RuntimeException("Connection failed", e);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        if (connection != null && connectionQueue.size() < POOL_SIZE) {
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("Thread was interrupted", e);
                throw new RuntimeException("Connection failed", e);
            }
        }
    }
}
