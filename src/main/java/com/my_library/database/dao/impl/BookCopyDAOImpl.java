package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.BookCopyDAO;
import com.my_library.model.BookCopy;
import com.my_library.model.CopyStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookCopyDAOImpl implements BookCopyDAO {

    private final ConnectionPool connectionPool;

    public BookCopyDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<BookCopy> findById(Long aLong) throws SQLException {
        return null;
    }

    @Override
    public List<BookCopy> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void save(BookCopy entity) throws SQLException {

    }

    @Override
    public void update(BookCopy entity) throws SQLException {

    }

    @Override
    public void deleteById(Long aLong) throws SQLException {

    }

    @Override
    public List<BookCopy> findByBookId(Long bookId) {
        return List.of();
    }

    @Override
    public List<BookCopy> findByInventoryNumber(Integer inventoryNumber) {
        return List.of();
    }

    @Override
    public List<BookCopy> findByStatus(CopyStatus status) {
        return List.of();
    }
}
