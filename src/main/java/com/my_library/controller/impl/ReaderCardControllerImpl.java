package com.my_library.controller.impl;

import com.my_library.controller.inrefaces.ReaderCardController;
import com.my_library.exception.ServiceException;
import com.my_library.model.ReaderCard;
import com.my_library.service.factory.FactoryService;
import com.my_library.service.interfaces.ReaderCardService;
import com.my_library.util.constants.UriConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class ReaderCardControllerImpl implements ReaderCardController {

    private static final Logger LOGGER = LogManager.getLogger(ReaderCardControllerImpl.class);
    private final ReaderCardService readerCardService = FactoryService.getInstance().getReaderCardService();

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String readerIdParam = req.getParameter("readerId");

        if (readerIdParam == null || readerIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Reader id is required");
            return;
        }

        Long readerId;

        try {
            readerId = Long.parseLong(readerIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reader id format");
            return;
        }

        try {
            Optional<ReaderCard> readerCard = readerCardService.findById(readerId);

            if (readerCard.isPresent()) {
                req.setAttribute("readerCard", readerCard.get());
                req.getRequestDispatcher("/jsp/readerCard.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Reader card not found");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error finding reader card by reader id={}", readerId, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to readerCard.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<ReaderCard> readerCards = readerCardService.findAll();

            req.setAttribute("readerCards", readerCards);
            req.getRequestDispatcher("/jsp/readerCards.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding all reader cards", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to readerCards.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String readerIdParam = req.getParameter("readerId");
        String cardNumber = req.getParameter("cardNumber");
        String issueDateParam = req.getParameter("issueDate");
        String expirationDateParam = req.getParameter("expirationDate");

        if (readerIdParam == null || readerIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Reader id is required");
            return;
        }

        Long readerId;

        try {
            readerId = Long.parseLong(readerIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reader id format");
            return;
        }

        if (cardNumber == null || cardNumber.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Card number is required");
            return;
        }

        if (issueDateParam == null || issueDateParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Issue date is required");
            return;
        }

        LocalDate issueDate;

        try {
            issueDate = LocalDate.parse(issueDateParam);
        } catch (DateTimeParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid issue date format");
            return;
        }

        LocalDate expirationDate = null;

        if (expirationDateParam != null && !expirationDateParam.isBlank()) {
            try {
                expirationDate = LocalDate.parse(expirationDateParam);
            } catch (DateTimeParseException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid expiration date format");
                return;
            }
        }

        ReaderCard readerCard = new ReaderCard();
        readerCard.setReaderId(readerId);
        readerCard.setCardNumber(cardNumber);
        readerCard.setIssueDate(issueDate);
        readerCard.setExpirationDate(expirationDate);

        try {
            readerCardService.save(readerCard);
            LOGGER.info("Reader card created: readerId={}, cardNumber={}", readerId, cardNumber);
        } catch (ServiceException e) {
            LOGGER.error("Error saving reader card", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.READER_CARD_FIND_ALL_URI);
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String readerIdParam = req.getParameter("readerId");
        String cardNumber = req.getParameter("cardNumber");
        String issueDateParam = req.getParameter("issueDate");
        String expirationDateParam = req.getParameter("expirationDate");

        if (readerIdParam == null || readerIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Reader id is required");
            return;
        }

        Long readerId;

        try {
            readerId = Long.parseLong(readerIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reader id format");
            return;
        }

        if (cardNumber == null || cardNumber.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Card number is required");
            return;
        }

        if (issueDateParam == null || issueDateParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Issue date is required");
            return;
        }

        LocalDate issueDate;

        try {
            issueDate = LocalDate.parse(issueDateParam);
        } catch (DateTimeParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid issue date format");
            return;
        }

        LocalDate expirationDate = null;

        if (expirationDateParam != null && !expirationDateParam.isBlank()) {
            try {
                expirationDate = LocalDate.parse(expirationDateParam);
            } catch (DateTimeParseException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid expiration date format");
                return;
            }
        }

        ReaderCard readerCard = new ReaderCard();
        readerCard.setReaderId(readerId);
        readerCard.setCardNumber(cardNumber);
        readerCard.setIssueDate(issueDate);
        readerCard.setExpirationDate(expirationDate);

        try {
            readerCardService.update(readerCard);
            LOGGER.info("Reader card updated: readerId={}, cardNumber={}", readerId, cardNumber);
        } catch (ServiceException e) {
            LOGGER.error("Error updating reader card with readerId={}", readerId, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.READER_CARD_FIND_ALL_URI);

    }

    @Override
    public void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String readerIdParam = req.getParameter("readerId");

        if (readerIdParam == null || readerIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Reader id is required");
            return;
        }

        Long readerId;

        try {
            readerId = Long.parseLong(readerIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reader id format");
            return;
        }

        try {
            readerCardService.deleteById(readerId);
            LOGGER.warn("Reader card deleted: readerId={}", readerId);
        } catch (ServiceException e) {
            LOGGER.error("Error deleting reader card with readerId={}", readerId, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.READER_CARD_FIND_ALL_URI);
    }
}
