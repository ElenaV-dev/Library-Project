package com.my_library.database.dao.factory;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.impl.*;
import com.my_library.database.dao.interfaces.*;

public class FactoryDAO {

    private static final FactoryDAO instance = new FactoryDAO();
    private final ConnectionPool connectionPool;

    private FactoryDAO() {
        connectionPool = ConnectionPool.getInstance();
    }

    public static FactoryDAO getInstance() {
        return instance;
    }

    public BookDAO getBookDAO() {
        return new BookDAOImpl(connectionPool);
    }

    public AuthorDAO getAuthorDAO() {
        return new AuthorDAOImpl(connectionPool);
    }

    public BookCopyDAO getBookCopyDAO() {
        return new BookCopyDAOImpl(connectionPool);
    }

    public UserDAO getUserDAO() {
        return new UserDAOImpl(connectionPool);
    }

    public ReaderCardDAO getReaderCardDAO() {
        return new ReaderCardDAOImpl(connectionPool);
    }

    public LoanDAO getLoanDAO() {
        return new LoanDAOImpl(connectionPool);
    }
}
