package com.my_library.service;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.BookDAO;
import com.my_library.model.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowAllBooksService implements Service {

    private FactoryDAO factoryDAO = FactoryDAO.getInstance();
    private BookDAO bookDAO = factoryDAO.getBookDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Book> books = bookDAO.findAll();
        request.setAttribute("books", books);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/books.jsp");
        requestDispatcher.forward(request, response);
    }
}
