package com.my_library.controller.impl;

import com.my_library.controller.inrefaces.BookCopyController;
import com.my_library.exception.ServiceException;
import com.my_library.model.BookCopy;
import com.my_library.model.CopyStatus;
import com.my_library.service.factory.FactoryService;
import com.my_library.service.interfaces.BookCopyService;
import com.my_library.util.constants.UriConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class BookCopyControllerImpl implements BookCopyController {

    private static final Logger LOGGER = LogManager.getLogger(BookCopyControllerImpl.class);
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
            Optional<BookCopy> bookCopy = bookCopyService.findById(id);

            if (bookCopy.isPresent()) {
                req.setAttribute("bookCopy", bookCopy.get());
                req.getRequestDispatcher("/jsp/bookCopy.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Book copy not found");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error finding book copy by id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to bookCopy.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<BookCopy> bookCopies = bookCopyService.findAll();

            req.setAttribute("bookCopies", bookCopies);
            req.getRequestDispatcher("/jsp/bookCopies.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding all book copies", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to bookCopies.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String bookIdParam = req.getParameter("bookId");
        String inventoryNumberParam = req.getParameter("inventoryNumber");
        String statusParam = req.getParameter("status");

        if (bookIdParam == null || bookIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book id is required");
            return;
        }

        Long bookId;

        try {
            bookId = Long.parseLong(bookIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book id format");
            return;
        }

        if (inventoryNumberParam == null || inventoryNumberParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Inventory number is required");
            return;
        }

        Integer inventoryNumber;

        try {
            inventoryNumber = Integer.parseInt(inventoryNumberParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid inventory number format");
            return;
        }

        CopyStatus status = null;

        if (statusParam != null && !statusParam.isBlank()) {
            try {
                status = CopyStatus.valueOf(statusParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status format");
                return;
            }
        }

        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookId(bookId);
        bookCopy.setInventoryNumber(inventoryNumber);
        bookCopy.setStatus(status);

        try {
            bookCopyService.save(bookCopy);
            LOGGER.info("Book copy created: id={}", bookCopy.getId());
        } catch (ServiceException e) {
            LOGGER.error("Error saving book copy", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.BOOK_COPY_FIND_ALL_URI);
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");
        String bookIdParam = req.getParameter("bookId");
        String inventoryNumberParam = req.getParameter("inventoryNumber");
        String statusParam = req.getParameter("status");

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

        if (bookIdParam == null || bookIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book id is required");
            return;
        }

        Long bookId;

        try {
            bookId = Long.parseLong(bookIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book id format");
            return;
        }

        if (inventoryNumberParam == null || inventoryNumberParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Inventory number is required");
            return;
        }

        Integer inventoryNumber;

        try {
            inventoryNumber = Integer.parseInt(inventoryNumberParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid inventoryNumber format");
            return;
        }

        CopyStatus status = null;

        if (statusParam != null && !statusParam.isBlank()) {
            try {
                status = CopyStatus.valueOf(statusParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status format");
                return;
            }
        }

        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(id);
        bookCopy.setBookId(bookId);
        bookCopy.setInventoryNumber(inventoryNumber);
        bookCopy.setStatus(status);

        try {
            bookCopyService.update(bookCopy);
            LOGGER.info("Book copy updated: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error updating book copy id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.BOOK_COPY_FIND_ALL_URI);
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
            bookCopyService.deleteById(id);
            LOGGER.warn("Book copy deleted: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error deleting book copy id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.BOOK_COPY_FIND_ALL_URI);
    }
}

