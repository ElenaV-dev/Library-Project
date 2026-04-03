package com.my_library.service.impl;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.BookDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.Book;
import com.my_library.service.interfaces.BookService;
import com.my_library.validator.BookValidator;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO = FactoryDAO.getInstance().getBookDAO();

    @Override
    public Optional<Book> findById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid book id");
        }

        try {
            return bookDAO.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find book", e);
        }
    }

    @Override
    public List<Book> findAll() throws ServiceException {

        try {
            return bookDAO.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find all books", e);
        }
    }

    @Override
    public void save(Book book) throws ServiceException {

        try {
            BookValidator.validate(book);

            Optional<Book> existing = bookDAO.findByIsbn(book.getIsbn());

            if (existing.isPresent()) {
                throw new ServiceException("Book with this ISBN already exists");
            }

            bookDAO.save(book);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to save book", e);
        }
    }

    @Override
    public void update(Book book) throws ServiceException {

        if (book.getId() == null || book.getId() <= 0) {
            throw new ServiceException("Invalid book id");
        }

        try {
            BookValidator.validate(book);

            Optional<Book> current = bookDAO.findById(book.getId());

            if (current.isEmpty()) {
                throw new ServiceException("Book not found");
            }

            Optional<Book> existing = bookDAO.findByIsbn(book.getIsbn());

            if (existing.isPresent() && !existing.get().getId().equals(book.getId())) {
                throw new ServiceException("Book with this ISBN already exists");
            }
            bookDAO.update(book);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update book", e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid book id");
        }

        try {
            bookDAO.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete book", e);
        }
    }
}
