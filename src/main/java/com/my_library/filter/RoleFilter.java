package com.my_library.filter;

import com.my_library.model.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RoleFilter implements Filter {

    private static final String ENTITY_BOOK = "book";
    private static final String ENTITY_AUTHOR = "author";
    private static final String ENTITY_BOOK_COPY = "bookCopy";
    private static final String ENTITY_USER = "user";
    private static final String ENTITY_READER_CARD = "readerCard";
    private static final String ENTITY_LOAN = "loan";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        System.out.println("METHOD = " + req.getMethod());
        System.out.println("CONTENT TYPE = " + req.getContentType());
        System.out.println("QUERY = " + req.getQueryString());

        String entity = req.getParameter("entity");
        String action = req.getParameter("action");

        HttpSession session = req.getSession(false);

        UserRole role = UserRole.GUEST;

        if (session != null) {
            Object roleObj = session.getAttribute("userRole");

            if (roleObj instanceof UserRole) {
                role = (UserRole) roleObj;
            }
        }

        if (entity == null || action == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if ("user".equals(entity) && "login".equals(action)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if ("user".equals(entity) && "register".equals(action)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if ("user".equals(entity) && "logout".equals(action)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!isAllowed(role, entity, action)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isAllowed(UserRole userRole, String entity, String action) {

        switch (userRole) {
            case ADMIN:
                return true;
            case LIBRARIAN:
                return checkLibrarian(entity, action);
            case READER:
                return checkReader(entity, action);
            case GUEST:
                return checkGuest(entity, action);
            default:
                return false;
        }
    }

    private boolean checkLibrarian(String entity, String action) {

        switch (entity) {
            case ENTITY_BOOK:
            case ENTITY_AUTHOR:
            case ENTITY_BOOK_COPY:
                return action.equals("findById") || action.equals("findAll");

            case ENTITY_USER:
                return action.equals("findAll") || action.equals("findById");

            case ENTITY_LOAN:
            case ENTITY_READER_CARD:
                return true;

            default:
                return false;
        }
    }

    private boolean checkReader(String entity, String action) {

        switch (entity) {
            case ENTITY_BOOK:
            case ENTITY_AUTHOR:
                return action.equals("findById") || action.equals("findAll");

            case ENTITY_LOAN:
                return action.equals("requestBook") || action.equals("returnBook");

            default:
                return false;
        }
    }

    private boolean checkGuest(String entity, String action) {

        switch (entity) {
            case ENTITY_BOOK:
            case ENTITY_AUTHOR:
                return action.equals("findAll") || action.equals("findByTitle");

            default:
                return false;
        }
    }
}
