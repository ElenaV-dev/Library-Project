package com.my_library.database.connection;

import com.my_library.util.constants.ErrorConstants;
import com.my_library.util.constants.MessageConstants;
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
            LOGGER.info(MessageConstants.TEST_CONNECTION_SUCCESSFUL);
        } catch (SQLException e) {
            LOGGER.error(ErrorConstants.FAILED_TO_CONNECT_TO_THE_DATABASE, e);
            throw new RuntimeException(ErrorConstants.ERROR_CONNECTING_TO_THE_DATABASE, e);
        }

        Connection connection;

        while (connectionQueue.size() < POOL_SIZE) {
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
        if (connection != null && connectionQueue.size() < POOL_SIZE) {
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
