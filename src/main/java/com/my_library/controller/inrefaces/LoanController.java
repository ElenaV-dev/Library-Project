package com.my_library.controller.inrefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LoanController extends GenericController {

    void issue(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void requestBook(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
