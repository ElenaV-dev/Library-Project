package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.User;
import com.my_library.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends GenericDAO<User, Long> {

    Optional<User> findByEmail(String email) throws DaoException;

    List<User> findByLastName(String lastName) throws DaoException;

    List<User> findByLastNameAndFirstName(String lastName, String firstName) throws DaoException;

    List<User> findByRole(UserRole role) throws DaoException;

    Optional<User> findByIin(String iin) throws DaoException;

    Optional<User> findByPhone(String phone) throws DaoException;
}
