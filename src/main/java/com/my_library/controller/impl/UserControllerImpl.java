package com.my_library.controller.impl;

import com.my_library.controller.inrefaces.UserController;
import com.my_library.exception.ServiceException;
import com.my_library.model.User;
import com.my_library.model.UserRole;
import com.my_library.service.factory.FactoryService;
import com.my_library.service.interfaces.UserService;
import com.my_library.util.constants.UriConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserControllerImpl implements UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserControllerImpl.class);
    private final UserService userService = FactoryService.getInstance().getUserService();

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
            Optional<User> user = userService.findById(id);

            if (user.isPresent()) {
                req.setAttribute("user", user.get());
                req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error finding user by id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to user.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            List<User> users = userService.findAll();

            req.setAttribute("users", users);
            req.getRequestDispatcher("/jsp/users.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOGGER.error("Error finding all users", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to users.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        String roleParam = req.getParameter("role");
        String iin = req.getParameter("iin");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        if (lastName == null || lastName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Last name is required");
            return;
        }

        if (firstName == null || firstName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "First name is required");
            return;
        }

        if (roleParam == null || roleParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Role is required");
            return;
        }

        UserRole role;

        try {
            role = UserRole.valueOf(roleParam.toUpperCase());
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role format");
            return;
        }

        if (iin == null || iin.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "IIN is required");
            return;
        }

        if (email == null || email.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required");
            return;
        }

        if (password == null || password.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password is required");
            return;
        }

        User user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setRole(role);
        user.setIin(iin);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        try {
            userService.save(user);
            LOGGER.info("User created: iin={}, role={}", iin, role);
        } catch (ServiceException e) {
            LOGGER.error("Error saving user last name={}, iin={}", lastName, iin, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.USER_FIND_ALL_URI);
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String idParam = req.getParameter("id");
        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        String roleParam = req.getParameter("role");
        String iin = req.getParameter("iin");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

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

        if (lastName == null || lastName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Last name is required");
            return;
        }

        if (firstName == null || firstName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "First name is required");
            return;
        }

        if (roleParam == null || roleParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Role is required");
            return;
        }

        UserRole role;

        try {
            role = UserRole.valueOf(roleParam.toUpperCase());
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role format");
            return;
        }

        if (iin == null || iin.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "IIN is required");
            return;
        }

        if (email == null || email.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required");
            return;
        }

        User user = new User();
        user.setId(id);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setRole(role);
        user.setIin(iin);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        try {
            userService.update(user);
            LOGGER.info("User updated: id={}, iin={}", id, iin);
        } catch (ServiceException e) {
            LOGGER.error("Error updating user id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.USER_FIND_ALL_URI);
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
            userService.deleteById(id);
            LOGGER.warn("User deleted: id={}", id);
        } catch (ServiceException e) {
            LOGGER.error("Error deleting user id={}", id, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/controller?entity=book&action=findAll");
    }

    @Override
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required");
            return;
        }

        if (password == null || password.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password is required");
            return;
        }

        try {
            Optional<User> userOpt = userService.login(email, password);

            if (userOpt.isPresent()) {
                HttpSession oldSession = req.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session = req.getSession(true);

                User user = userOpt.get();
                session.setAttribute("user", user);
                session.setAttribute("userRole", user.getRole());

                switch (user.getRole()) {

                    case ADMIN, READER:
                        resp.sendRedirect(req.getContextPath() +
                                "/controller?entity=book&action=findAll");
                        break;

                    case LIBRARIAN:
                        resp.sendRedirect(req.getContextPath() +
                                "/controller?entity=loan&action=findAll");
                        break;

                    default:
                        resp.sendRedirect(req.getContextPath() + "/index.jsp");
                }
            } else {
                req.setAttribute("error", "Invalid email or password");
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
            }

        } catch (ServiceException e) {
            LOGGER.error("Error login", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } catch (ServletException e) {
            LOGGER.error("Error forwarding to login.jsp", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "View error");
        }
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/controller");
    }

    @Override
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String lastName = req.getParameter("lastName");
        String firstName = req.getParameter("firstName");
        String iin = req.getParameter("iin");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        if (lastName == null || lastName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Last name is required");
            return;
        }

        if (firstName == null || firstName.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "First name is required");
            return;
        }

        if (iin == null || iin.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "IIN is required");
            return;
        }

        if (email == null || email.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required");
            return;
        }

        if (password == null || password.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password is required");
            return;
        }

        try {
            userService.register(lastName, firstName, iin, email, phone, password);
            LOGGER.info("User registered: iin={}", iin);
        } catch (ServiceException e) {
            LOGGER.error("Error registering user last name={}, iin={}", lastName, iin, e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
        resp.sendRedirect(UriConstants.BOOK_FIND_ALL_URI);
    }
}

