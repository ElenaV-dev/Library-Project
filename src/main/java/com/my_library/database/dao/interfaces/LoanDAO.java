package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.Loan;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoanDAO extends GenericDAO<Loan, Long> {

    Optional<Loan> findById(Long id, Connection conn) throws DaoException;

    void deleteById(Long id, Connection conn) throws DaoException;

    void save(Loan loan, Connection conn) throws DaoException;
}
