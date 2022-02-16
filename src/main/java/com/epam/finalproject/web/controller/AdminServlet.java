package com.epam.finalproject.web.controller;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.model.User;
import com.epam.finalproject.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final IUserService userService = AppContext.getInstance().getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = String.valueOf(req.getSession().getAttribute("role"));
        Integer id = (Integer) req.getSession().getAttribute("id");

        if (role.equals("ADMIN") || role.equals("CASHIER") || role.equals("SENIOR_CASHIER")|| role.equals("MERCHANT")) {
            User user = userService.findById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/admin/admin.jsp").forward(req, resp);
            req.getSession().setAttribute("path", req.getRequestURI());
        } else {
            resp.sendRedirect("/login");
        }
    }
}
