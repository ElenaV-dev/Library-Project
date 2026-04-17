package com.my_library.controller.inrefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserController extends GenericController {

    void login(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void register(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
