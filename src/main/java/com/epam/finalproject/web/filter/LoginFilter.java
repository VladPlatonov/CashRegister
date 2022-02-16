package com.epam.finalproject.web.filter;


import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.model.UserRole;
import com.epam.finalproject.service.IUserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;


@WebFilter("/login")
public class LoginFilter implements Filter {
    private final IUserService userService = AppContext.getInstance().getUserService();

    @Override
    public void init(FilterConfig filterConfig){}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {
            res.sendRedirect("/");
        }
        else if(login==null){
            req.getRequestDispatcher("WEB-INF/client/login.jsp").forward(req, res);
        }
        else if (userService.checkUser(login, password)) {
            UserRole role = userService.findByLoginAndPassword(login, password).getUserRole();
            int id = userService.findByLoginAndPassword(login, password).getId();
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            req.getSession().setAttribute("id", id);
            res.sendRedirect("/");
        } else if(userService.isValidLogin(login)) {
            req.setAttribute("WrongPass","true");
            req.getRequestDispatcher("WEB-INF/client/login.jsp").forward(req, res);
        }
        else{
            req.setAttribute("NotFound","true");
            req.getRequestDispatcher("WEB-INF/client/login.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}
