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

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final IUserService userService = AppContext.getInstance().getUserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = String.valueOf(req.getSession().getAttribute("role"));
        Integer id = (Integer) req.getSession().getAttribute("id");

        if (role.equals("ADMIN")) {
            User user = userService.findById(id);
            int page = 1;
            int recordsPerPage = 5;
            if(req.getParameter("page") != null)
                page = Integer.parseInt(req.getParameter("page"));
            int noOfRecords = userService.getNumberOfRows();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("user", user);
            req.setAttribute("users", userService.findUsersUsingLimitAndOffset(page,recordsPerPage));
            req.getRequestDispatcher("WEB-INF/admin/users.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
    /**
     * take param userAction and split to array
     * first index is action
     * second index is userId
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String [] userAction = req.getParameter("userAction").split("!");
        User user = userService.findById(Integer.parseInt(userAction[0]));
        if(userAction[1].equals("updateUser")) {
            System.out.println(req.getParameter("UserRole"));
            user.setUserRole(UserRole.valueOf(req.getParameter("UserRole")));
            userService.update(user);
        }
        if(userAction[1].equals("deleteUser"))
            userService.deleteById(user.getId());
        resp.sendRedirect("/users");
    }
}