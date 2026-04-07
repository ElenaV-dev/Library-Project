package com.my_library.controller.inrefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface GenericController {

    void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void save(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
