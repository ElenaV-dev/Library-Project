package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {

    Optional<T> findById(ID id) throws DaoException;

    List<T> findAll() throws DaoException;

    void save(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void deleteById(ID id) throws DaoException;
}
