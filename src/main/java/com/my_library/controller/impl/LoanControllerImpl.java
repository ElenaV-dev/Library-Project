package com.my_library.controller.impl;

import com.my_library.controller.inrefaces.LoanController;
import com.my_library.exception.ServiceException;
import com.my_library.model.Loan;
import com.my_library.model.User;
import com.my_library.service.factory.FactoryService;
import com.my_library.service.interfaces.LoanService;
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

public class LoanControllerImpl implements LoanController {

    private static final Logger LOGGER = LogManager.getLogger(LoanControllerImpl.class);
    private final LoanService loanService = FactoryService.getInstance().getLoanService();

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
            Optional<Loan> loan = loanService.findById(id);

            if (loan.isPresent()) {
                req.setAttribute("loan", loan.get());
                req.getRequestDispatcher("/jsp/loan.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Loan not found");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error finding loan by id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to loan.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<Loan> loans = loanService.findAll();

            req.setAttribute("loans", loans);
            req.getRequestDispatcher("/jsp/loans.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding all loans", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to loans.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String bookCopyIdParam = req.getParameter("bookCopyId");
        String userIdParam = req.getParameter("userId");
        String loanDateParam = req.getParameter("loanDate");
        String returnDateParam = req.getParameter("returnDate");

        if (bookCopyIdParam == null || bookCopyIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book copy id is required");
            return;
        }

        Long bookCopyId;

        try {
            bookCopyId = Long.parseLong(bookCopyIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book copy id format");
            return;
        }

        if (userIdParam == null || userIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User id is required");
            return;
        }

        Long userId;

        try {
            userId = Long.parseLong(userIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user id format");
            return;
        }

        if (loanDateParam == null || loanDateParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Loan date is required");
            return;
        }

        LocalDate loanDate;

        try {
            loanDate = LocalDate.parse(loanDateParam);
        } catch (DateTimeParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid loan date format");
            return;
        }

        LocalDate returnDate = null;

        if (returnDateParam != null && !returnDateParam.isBlank()) {
            try {
                returnDate = LocalDate.parse(returnDateParam);
            } catch (DateTimeParseException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid return date format");
                return;
            }
        }

        Loan loan = new Loan();
        loan.setBookCopyId(bookCopyId);
        loan.setUserId(userId);
        loan.setLoanDate(loanDate);
        loan.setReturnDate(returnDate);

        try {
            loanService.save(loan);
            LOGGER.info("Loan created: bookCopyId={}, userId={}", bookCopyId, userId);
        } catch (ServiceException e) {
            LOGGER.error("Error saving loan", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.LOAN_FIND_ALL_URI);
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");
        String bookCopyIdParam = req.getParameter("bookCopyId");
        String userIdParam = req.getParameter("userId");
        String loanDateParam = req.getParameter("loanDate");
        String returnDateParam = req.getParameter("returnDate");

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

        if (bookCopyIdParam == null || bookCopyIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book copy id is required");
            return;
        }

        Long bookCopyId;

        try {
            bookCopyId = Long.parseLong(bookCopyIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book copy id format");
            return;
        }

        if (userIdParam == null || userIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User id is required");
            return;
        }

        Long userId;

        try {
            userId = Long.parseLong(userIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user id format");
            return;
        }

        if (loanDateParam == null || loanDateParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Loan date is required");
            return;
        }

        LocalDate loanDate;

        try {
            loanDate = LocalDate.parse(loanDateParam);
        } catch (DateTimeParseException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid loan date format");
            return;
        }

        LocalDate returnDate = null;

        if (returnDateParam != null && !returnDateParam.isBlank()) {
            try {
                returnDate = LocalDate.parse(returnDateParam);
            } catch (DateTimeParseException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid return date format");
                return;
            }
        }

        Loan loan = new Loan();
        loan.setId(id);
        loan.setBookCopyId(bookCopyId);
        loan.setUserId(userId);
        loan.setLoanDate(loanDate);
        loan.setReturnDate(returnDate);

        try {
            loanService.update(loan);
            LOGGER.info("Loan updated: id={}, bookCopyId={}, userId={}", id, bookCopyId, userId);
        } catch (ServiceException e) {
            LOGGER.error("Error updating loan id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.LOAN_FIND_ALL_URI);

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
            loanService.deleteById(id);
            LOGGER.warn("Loan deleted: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error deleting loan id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.LOAN_FIND_ALL_URI);
    }

    @Override
    public void issue(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
            loanService.issueLoan(id);
            LOGGER.warn("Loan issued: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error issuing loan id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/controller?entity=loan&action=findAll");
    }

    @Override
    public void requestBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bookIdParam = req.getParameter("bookId");

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

        try {
            User user = (User) req.getSession().getAttribute("user");
            Long userId = user.getId();

            loanService.requestBook(bookId, userId);
            LOGGER.info("Loan requested: book id={}", bookId);
        } catch (ServiceException e) {
            LOGGER.error("Error requesting book copy bookId={}", bookId, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(req.getContextPath() +
                "/controller?entity=book&action=findById&id="
                + bookId + "&success=requested");
    }
}

