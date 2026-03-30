package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.Author;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface AuthorDAO extends GenericDAO<Author, UUID> {

    List<Author> findByLastName(String lastName) throws DaoException;

    List<Author> findByLastNameAndFirstName(String lastName, String firstName) throws DaoException;

}
