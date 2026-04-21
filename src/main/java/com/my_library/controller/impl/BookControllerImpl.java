package com.my_library.controller.impl;

import com.my_library.controller.inrefaces.BookController;
import com.my_library.exception.ServiceException;
import com.my_library.model.Book;
import com.my_library.service.factory.FactoryService;
import com.my_library.service.interfaces.BookCopyService;
import com.my_library.service.interfaces.BookService;
import com.my_library.util.constants.UriConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookControllerImpl implements BookController {

    private static final Logger LOGGER = LogManager.getLogger(BookControllerImpl.class);
    private final BookService bookService = FactoryService.getInstance().getBookService();
    private final BookCopyService bookCopyService = FactoryService.getInstance().getBookCopyService();


    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id is required");
            return;
        }

        Long id;

        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id format");
            return;
        }

        try {
            Optional<Book> book = bookService.findById(id);

            int available = bookCopyService.countAvailableCopiesByBookId(id);

            if (book.isPresent()) {
                req.setAttribute("book", book.get());
                req.setAttribute("availableCopies", available);
                req.getRequestDispatcher("/jsp/book.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error finding book by id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to book.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<Book> books = bookService.findAll();

            req.setAttribute("books", books);
            req.getRequestDispatcher("/jsp/books.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding all books", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to books.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String title = req.getParameter("title");
        String yearParam = req.getParameter("year");
        String isbn = req.getParameter("isbn");
        String publisher = req.getParameter("publisher");

        if (title == null || title.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Title is required");
            return;
        }

        Integer year = null;

        if (yearParam != null && !yearParam.isBlank()) {
            try {
                year = Integer.parseInt(yearParam);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid year format");
                return;
            }
        }

        if (isbn == null || isbn.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ISBN is required");
            return;
        }

        Book book = new Book();
        book.setTitle(title);
        book.setYear(year);
        book.setIsbn(isbn);
        book.setPublisher(publisher);

        try {
            bookService.save(book);
            LOGGER.info("Book created: id={}, isbn={}", book.getId(), book.getIsbn());
        } catch (ServiceException e) {
            LOGGER.error("Error saving book isbn={}", isbn, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/controller?entity=book&action=findAll");
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");
        String title = req.getParameter("title");
        String yearParam = req.getParameter("year");
        String isbn = req.getParameter("isbn");
        String publisher = req.getParameter("publisher");

        if (idParam == null || idParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id is required");
            return;
        }

        Long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id format");
            return;
        }

        if (title == null || title.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Title is required");
            return;
        }

        Integer year = null;

        if (yearParam != null && !yearParam.isBlank()) {
            try {
                year = Integer.parseInt(yearParam);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid year format");
                return;
            }
        }

        if (isbn == null || isbn.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ISBN is required");
            return;
        }

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setYear(year);
        book.setIsbn(isbn);
        book.setPublisher(publisher);

        try {
            bookService.update(book);
            LOGGER.info("Book updated: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error updating book id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/controller?entity=book&action=findAll");
    }

    @Override
    public void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id is required");
            return;
        }

        Long id;

        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id format");
            return;
        }

        try {
            bookService.deleteById(id);
            LOGGER.warn("Book deleted: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error deleting book id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/controller?entity=book&action=findAll");
    }

    public void findAllForIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<Book> books = bookService.findAll();

            req.setAttribute("books", books);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding all books", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to index.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void findByTitle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");

        if (title == null || title.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Title is required");
            return;
        }

        try {
            List<Book> books = bookService.findByTitle(title);

            req.setAttribute("books", books);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding books by title: {}", title, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to index.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void showUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id is required");
            return;
        }

        Long id;

        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id format");
            return;
        }

        try {
            Optional<Book> book = bookService.findById(id);

            if (book.isPresent()) {
                req.setAttribute("book", book.get());
                req.getRequestDispatcher("/jsp/book-edit.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error showing update page for book id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to book-edit.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }
}

