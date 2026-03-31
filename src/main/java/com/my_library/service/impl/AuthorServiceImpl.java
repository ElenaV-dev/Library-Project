package com.my_library.service.impl;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.AuthorDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.Author;
import com.my_library.service.interfaces.AuthorService;
import com.my_library.validator.AuthorValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO = FactoryDAO.getInstance().getAuthorDAO();

    @Override
    public Optional<Author> findById(UUID uuid) throws ServiceException {
        if (uuid == null) {
            throw new ServiceException("Invalid author id");
        }

        try {
            return authorDAO.findById(uuid);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find author", e);
        }
    }

    @Override
    public List<Author> findAll() throws ServiceException {
        try {
            return authorDAO.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find all authors", e);
        }
    }

    @Override
    public void save(Author author) throws ServiceException {
        if (author == null) {
            throw new ServiceException("Author is null");
        }

        try {
            AuthorValidator.validate(author);
            authorDAO.save(author);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to save author", e);
        }
    }

    @Override
    public void update(Author author) throws ServiceException {
        if (author == null) {
            throw new ServiceException("Author is null");
        }

        if (author.getUuid() == null) {
            throw new ServiceException("Invalid author id");
        }

        try {
            AuthorValidator.validate(author);

            Optional<Author> existing = authorDAO.findById(author.getUuid());

            if (existing.isEmpty()) {
                throw new ServiceException("Author not found");
            }
            authorDAO.update(author);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update author", e);
        }
    }

    @Override
    public void deleteById(UUID uuid) throws ServiceException {
        if (uuid == null) {
            throw new ServiceException("Invalid author id");
        }

        try {
            authorDAO.deleteById(uuid);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete author", e);
        }
    }
}
