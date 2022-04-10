package com.epam.finalproject.web.controller;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.model.User;
import com.epam.finalproject.service.IInvoiceService;
import com.epam.finalproject.service.IProductService;
import com.epam.finalproject.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/createInvoice")
public class CreateInvoiceServlet extends HttpServlet {
    private final IUserService userService = AppContext.getInstance().getUserService();
    private final IProductService productService = AppContext.getInstance().getProductService();
    private final IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = String.valueOf(req.getSession().getAttribute("role"));
        Integer id = (Integer) req.getSession().getAttribute("id");
        if (role.equals("ADMIN") || role.equals("CASHIER") || role.equals("SENIOR_CASHIER")) {
            int page = 1;
            int recordsPerPage = 5;
            int noOfRecords = productService.getNumberOfRows();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            if (req.getParameter("page") != null)
                page = Integer.parseInt(req.getParameter("page"));
            User user = userService.findById(id);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("user", user);
            req.setAttribute("products", productService.findProductsUsingLimitAndOffset(page, recordsPerPage));
            req.getRequestDispatcher("WEB-INF/admin/createInvoice.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }

    /**
     * take an array of product codes, filter on not empty
     * and  receive value quantity of this product then call the service
     * if the invoice is created redirect to invoices
     * else return createInvoice with error
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Integer id = (Integer) req.getSession().getAttribute("id");
        String[] productCodes = req.getParameterValues("setQuantity");
        int page = 1;
        int recordsPerPage = 5;
        int noOfRecords = productService.getNumberOfRows();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        if (productCodes != null) {
            User user = userService.findById(id);
            Map<String, Integer> products = new HashMap<>();
            for (int i = 0; i < productCodes.length - 1; i += 2)
                if (Integer.parseInt(productCodes[i + 1]) > 0)
                    products.put(productCodes[i], Integer.parseInt(productCodes[i + 1]));

            if (products.isEmpty() || !invoiceService.createInvoice(products, System.currentTimeMillis(), user)) {
                req.setAttribute("isValid", true);
                req.setAttribute("noOfPages", noOfPages);
                req.setAttribute("currentPage", page);
                req.setAttribute("products", productService.findProductsUsingLimitAndOffset(page, recordsPerPage));
                req.getRequestDispatcher("WEB-INF/admin/createInvoice.jsp").forward(req, resp);
            }
            }
            resp.sendRedirect("/invoices");
        }
    }

