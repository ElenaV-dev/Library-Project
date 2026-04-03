package com.my_library.service.impl;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.UserDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.User;
import com.my_library.service.interfaces.UserService;
import com.my_library.validator.UserValidator;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = FactoryDAO.getInstance().getUserDAO();

    @Override
    public Optional<User> findById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid user id");
        }

        try {
            return userDAO.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find user", e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {

        try {
            return userDAO.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find all users", e);
        }
    }

    @Override
    public void save(User user) throws ServiceException {

        try {
            UserValidator.validate(user);
            userDAO.save(user);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to save user", e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {

        if (user.getId() == null || user.getId() <= 0) {
            throw new ServiceException("Invalid user id");
        }

        try {
            UserValidator.validate(user);

            Optional<User> current = userDAO.findById(user.getId());

            if (current.isEmpty()) {
                throw new ServiceException("User not found");
            }

            userDAO.update(user);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update user", e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid user id");
        }

        try {
            userDAO.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete user", e);
        }
    }
}
