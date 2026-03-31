package com.my_library.service.impl;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.BookCopyDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.Book;
import com.my_library.model.BookCopy;
import com.my_library.service.interfaces.BookCopyService;
import com.my_library.validator.BookCopyValidator;

import java.util.List;
import java.util.Optional;

public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyDAO bookCopyDAO = FactoryDAO.getInstance().getBookCopyDAO();

    @Override
    public Optional<BookCopy> findById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid book copy id");
        }

        try {
            return bookCopyDAO.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find book copy", e);
        }
    }

    @Override
    public List<BookCopy> findAll() throws ServiceException {

        try {
            return bookCopyDAO.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find all book copies", e);
        }
    }

    @Override
    public void save(BookCopy bookCopy) throws ServiceException {

        if (bookCopy == null) {
            throw new ServiceException("Book copy is null");
        }

        try {
            BookCopyValidator.validate(bookCopy);
            bookCopyDAO.save(bookCopy);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to save book copy", e);
        }
    }

    @Override
    public void update(BookCopy bookCopy) throws ServiceException {

        if (bookCopy == null) {
            throw new ServiceException("Book copy is null");
        }

        if (bookCopy.getId() == null || bookCopy.getId() <= 0) {
            throw new ServiceException("Invalid book copy id");
        }

        try {
            BookCopyValidator.validate(bookCopy);

            Optional<BookCopy> existing = bookCopyDAO.findById(bookCopy.getId());

            if (existing.isEmpty()) {
                throw new ServiceException("Book copy not found");
            }

            bookCopyDAO.update(bookCopy);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update book copy", e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("Invalid book copy id");
        }

        try {
            bookCopyDAO.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete book copy", e);
        }
    }
}
