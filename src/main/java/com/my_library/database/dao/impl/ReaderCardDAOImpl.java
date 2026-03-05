package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.ReaderCardDAO;
import com.my_library.model.ReaderCard;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReaderCardDAOImpl implements ReaderCardDAO {

    private final ConnectionPool connectionPool;

    public ReaderCardDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<ReaderCard> findById(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<ReaderCard> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void save(ReaderCard entity) throws SQLException {

    }

    @Override
    public void update(ReaderCard entity) throws SQLException {

    }

    @Override
    public void deleteById(Long aLong) throws SQLException {

    }

    @Override
    public ReaderCard findByCardNumber(String cardNumber) {
        return null;
    }

    @Override
    public List<ReaderCard> findByIssueDate(Date issueDate) {
        return List.of();
    }
}
