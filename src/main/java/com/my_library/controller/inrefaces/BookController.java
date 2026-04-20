package com.my_library.controller.inrefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BookController extends GenericController {

    void findAllForIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void findByTitle(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void showUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
