package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.LoanDAO;
import com.my_library.model.Loan;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LoanDAOImpl implements LoanDAO {

    private final ConnectionPool connectionPool;

    public LoanDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Loan> findById(Long aLong) throws SQLException {
        return null;
    }

    @Override
    public List<Loan> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void save(Loan entity) throws SQLException {

    }

    @Override
    public void update(Loan entity) throws SQLException {

    }

    @Override
    public void deleteById(Long aLong) throws SQLException {

    }

    @Override
    public List<Loan> findByBookCopyId(Long bookCopyId) {
        return List.of();
    }

    @Override
    public List<Loan> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Loan> findByLoanDate(Date loanDate) {
        return List.of();
    }

    @Override
    public List<Loan> findByLoanDateBetween(Date start, Date end) {
        return List.of();
    }

    @Override
    public List<Loan> findByReturnDateIsNull() {
        return List.of();
    }
}
