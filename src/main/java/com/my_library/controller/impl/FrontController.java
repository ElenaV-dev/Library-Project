package com.my_library.controller.impl;

import com.my_library.controller.inrefaces.*;
import com.my_library.database.connection.ConnectionPool;
import com.my_library.util.constants.ErrorConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    private static final String ENTITY_BOOK = "book";
    private static final String ENTITY_AUTHOR = "author";
    private static final String ENTITY_BOOK_COPY = "bookCopy";
    private static final String ENTITY_USER = "user";
    private static final String ENTITY_READER_CARD = "readerCard";
    private static final String ENTITY_LOAN = "loan";

    private static final String ACTION_FIND_BY_ID = "findById";
    private static final String ACTION_FIND_ALL = "findAll";
    private static final String ACTION_SAVE = "save";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DELETE_BY_ID = "deleteById";
    private static final String ACTION_LOGIN = "login";
    private static final String ACTION_LOGOUT = "logout";
    private static final String ACTION_REGISTER = "register";
    private static final String ACTION_FIND_BY_TITLE = "findByTitle";
    private static final String ACTION_SHOW_UPDATE = "showUpdate";
    private static final String ACTION_ISSUE = "issue";
    private static final String ACTION_REQUEST_BOOK = "requestBook";

    private final BookController bookController = new BookControllerImpl();
    private final AuthorController authorController = new AuthorControllerImpl();
    private final BookCopyController bookCopyController = new BookCopyControllerImpl();
    private final UserController userController = new UserControllerImpl();
    private final ReaderCardController readerCardController = new ReaderCardControllerImpl();
    private final LoanController loanController = new LoanControllerImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String entity = req.getParameter("entity");
        String action = req.getParameter("action");

        if (entity == null && action == null) {
            bookController.findAllForIndex(req, resp);
            return;
        }

        if (entity == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Entity is required");
            return;
        }

        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action is required");
            return;
        }

        try {
            route(entity, action, req, resp);
        } catch (Exception e) {
            LOGGER.error("Unhandled error in FrontController", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    private void route(String entity, String action, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        switch (entity) {
            case ENTITY_BOOK:
                handleBook(action, req, resp);
                break;
            case ENTITY_AUTHOR:
                handleAuthor(action, req, resp);
                break;
            case ENTITY_BOOK_COPY:
                handleBookCopy(action, req, resp);
                break;
            case ENTITY_USER:
                handleUser(action, req, resp);
                break;
            case ENTITY_READER_CARD:
                handleReaderCard(action, req, resp);
                break;
            case ENTITY_LOAN:
                handleLoan(action, req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown entity");
        }
    }

    private void handleBook(String action, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        switch (action) {
            case ACTION_FIND_BY_ID:
                bookController.findById(req, resp);
                break;
            case ACTION_FIND_ALL:
                bookController.findAll(req, resp);
                break;
            case ACTION_SAVE:
                bookController.save(req, resp);
                break;
            case ACTION_UPDATE:
                bookController.update(req, resp);
                break;
            case ACTION_DELETE_BY_ID:
                bookController.deleteById(req, resp);
                break;
            case ACTION_FIND_BY_TITLE:
                bookController.findByTitle(req, resp);
                break;
            case ACTION_SHOW_UPDATE:
                bookController.showUpdate(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown book action");
        }
    }

    private void handleAuthor(String action, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        switch (action) {
            case ACTION_FIND_BY_ID:
                authorController.findById(req, resp);
                break;
            case ACTION_FIND_ALL:
                authorController.findAll(req, resp);
                break;
            case ACTION_SAVE:
                authorController.save(req, resp);
                break;
            case ACTION_UPDATE:
                authorController.update(req, resp);
                break;
            case ACTION_DELETE_BY_ID:
                authorController.deleteById(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown author action");
        }
    }

    private void handleBookCopy(String action, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        switch (action) {
            case ACTION_FIND_BY_ID:
                bookCopyController.findById(req, resp);
                break;
            case ACTION_FIND_ALL:
                bookCopyController.findAll(req, resp);
                break;
            case ACTION_SAVE:
                bookCopyController.save(req, resp);
                break;
            case ACTION_UPDATE:
                bookCopyController.update(req, resp);
                break;
            case ACTION_DELETE_BY_ID:
                bookCopyController.deleteById(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown book copy action");
        }
    }

    private void handleUser(String action, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        switch (action) {
            case ACTION_FIND_BY_ID:
                userController.findById(req, resp);
                break;
            case ACTION_FIND_ALL:
                userController.findAll(req, resp);
                break;
            case ACTION_SAVE:
                userController.save(req, resp);
                break;
            case ACTION_UPDATE:
                userController.update(req, resp);
                break;
            case ACTION_DELETE_BY_ID:
                userController.deleteById(req, resp);
                break;
            case ACTION_LOGIN:
                userController.login(req, resp);
                break;
            case ACTION_LOGOUT:
                userController.logout(req, resp);
                break;
            case ACTION_REGISTER:
                userController.register(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown user action");
        }
    }

    private void handleReaderCard(String action, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        switch (action) {
            case ACTION_FIND_BY_ID:
                readerCardController.findById(req, resp);
                break;
            case ACTION_FIND_ALL:
                readerCardController.findAll(req, resp);
                break;
            case ACTION_SAVE:
                readerCardController.save(req, resp);
                break;
            case ACTION_UPDATE:
                readerCardController.update(req, resp);
                break;
            case ACTION_DELETE_BY_ID:
                readerCardController.deleteById(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown reader card action");
        }
    }

    private void handleLoan(String action, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        switch (action) {
            case ACTION_FIND_BY_ID:
                loanController.findById(req, resp);
                break;
            case ACTION_FIND_ALL:
                loanController.findAll(req, resp);
                break;
            case ACTION_SAVE:
                loanController.save(req, resp);
                break;
            case ACTION_UPDATE:
                loanController.update(req, resp);
                break;
            case ACTION_DELETE_BY_ID:
                loanController.deleteById(req, resp);
                break;
            case ACTION_ISSUE:
                loanController.issue(req, resp);
                break;
            case ACTION_REQUEST_BOOK:
                loanController.requestBook(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown loan action");
        }
    }
}
