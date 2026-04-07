package com.my_library.controller.impl;

import com.my_library.controller.inrefaces.AuthorController;
import com.my_library.exception.ServiceException;
import com.my_library.model.Author;
import com.my_library.service.factory.FactoryService;
import com.my_library.service.interfaces.AuthorService;
import com.my_library.util.constants.UriConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthorControllerImpl implements AuthorController {

    private static final Logger LOGGER = LogManager.getLogger(AuthorControllerImpl.class);
    private final AuthorService authorService = FactoryService.getInstance().getAuthorService();

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id is required");
            return;
        }

        UUID id;

        try {
            id = UUID.fromString(idParam);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid UUID format");
            return;
        }

        try {
            Optional<Author> author = authorService.findById(id);

            if (author.isPresent()) {
                req.setAttribute("author", author.get());
                req.getRequestDispatcher("/author.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Author not found");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error finding author by id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to author.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<Author> authors = authorService.findAll();

            req.setAttribute("authors", authors);
            req.getRequestDispatcher("/authors.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding all authors", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to authors.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        String lifeYears = req.getParameter("lifeYears");

        if (lastName == null || lastName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Last name is required");
            return;
        }

        if (firstName == null || firstName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "First name is required");
            return;
        }

        Author author = new Author();
        author.setLastName(lastName);
        author.setFirstName(firstName);
        author.setLifeYears(lifeYears);

        try {
            authorService.save(author);
            LOGGER.info("Author created: id={}", author.getUuid());
        } catch (ServiceException e) {
            LOGGER.error("Error saving author", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.AUTHOR_FIND_ALL_URI);
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String uuidParam = req.getParameter("uuid");
        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        String lifeYears = req.getParameter("lifeYears");

        if (uuidParam == null || uuidParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id is required");
            return;
        }

        UUID id;
        try {
            id = UUID.fromString(uuidParam);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid UUID format");
            return;
        }

        if (lastName == null || lastName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Last name is required");
            return;
        }

        if (firstName == null || firstName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "First name is required");
            return;
        }

        Author author = new Author();
        author.setUuid(id);
        author.setLastName(lastName);
        author.setFirstName(firstName);
        author.setLifeYears(lifeYears);

        try {
            authorService.update(author);
            LOGGER.info("Author updated: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error updating author id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.AUTHOR_FIND_ALL_URI);
    }

    @Override
    public void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id is required");
            return;
        }

        UUID id;

        try {
            id = UUID.fromString(idParam);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid UUID format");
            return;
        }

        try {
            authorService.deleteById(id);
            LOGGER.warn("Author deleted: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error deleting author id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.AUTHOR_FIND_ALL_URI);
    }
}

