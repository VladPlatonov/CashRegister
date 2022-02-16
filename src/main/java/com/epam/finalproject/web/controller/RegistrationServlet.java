package com.epam.finalproject.web.controller;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.model.User;
import com.epam.finalproject.model.UserRole;
import com.epam.finalproject.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    private final IUserService userService = AppContext.getInstance().getUserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/client/registration.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setUserRole(UserRole.USER);
        if(!userService.isNotNullSignUp(user)){
            req.setAttribute("IsValid","null");
            req.getRequestDispatcher("WEB-INF/client/registration.jsp").forward(req, resp);
        }
        if(userService.isValidLogin(user.getLogin())) {
            req.setAttribute("WrongLogin","true");
            req.getRequestDispatcher("WEB-INF/client/registration.jsp").forward(req, resp);
        }
        else{
            userService.create(user);
            resp.sendRedirect("/");
        }
    }
}

