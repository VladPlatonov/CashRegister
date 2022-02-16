package com.epam.finalproject.web.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final HttpSession session = req.getSession();
        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");
        session.removeAttribute("id");
        resp.sendRedirect("/login");
    }
}
