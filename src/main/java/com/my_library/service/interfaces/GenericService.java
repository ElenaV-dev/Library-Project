package com.my_library.service.interfaces;

import com.my_library.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    Optional<T> findById(ID id) throws ServiceException;

    List<T> findAll() throws ServiceException;

    void save(T entity) throws ServiceException;

    void update(T entity) throws ServiceException;

    void deleteById(ID id) throws ServiceException;
}
