package com.my_library.service.impl;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.UserDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.User;
import com.my_library.model.UserRole;
import com.my_library.service.interfaces.UserService;
import com.my_library.validator.UserValidator;
import org.mindrot.jbcrypt.BCrypt;

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

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);

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
            UserValidator.validateForUpdate(user);

            Optional<User> currentOpt = userDAO.findById(user.getId());

            if (currentOpt.isEmpty()) {
                throw new ServiceException("User not found");
            }

            User current = currentOpt.get();

            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                user.setPassword(hashedPassword);
            } else {
                user.setPassword(current.getPassword());
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

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        try {
            UserValidator.validateEmail(email);

            if (password == null || password.isBlank()) {
                throw new ServiceException("Password is empty");
            }

            Optional<User> userOpt = userDAO.findByEmail(email);

            if (userOpt.isEmpty()) {
                return Optional.empty();
            }

            User user = userOpt.get();

            if (BCrypt.checkpw(password, user.getPassword())) {
                return Optional.of(user);
            }

            return Optional.empty();
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Login failed", e);
        }
    }

    @Override
    public User register(String lastName, String firstName, String iin, String email, String phone, String password) throws ServiceException {
        try {
            if (userDAO.findByEmail(email).isPresent()) {
                throw new ServiceException("User already exists");
            }

            User user = new User();
            user.setLastName(lastName);
            user.setFirstName(firstName);
            user.setRole(UserRole.READER);
            user.setIin(iin);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);

            UserValidator.validate(user);

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            user.setPassword(hashedPassword);

            userDAO.save(user);
            return user;
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Create user failed", e);
        }
    }
}
