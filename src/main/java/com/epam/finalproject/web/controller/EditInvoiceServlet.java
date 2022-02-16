package com.epam.finalproject.web.controller;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.model.Invoice;
import com.epam.finalproject.model.Order;
import com.epam.finalproject.model.User;
import com.epam.finalproject.service.IInvoiceService;
import com.epam.finalproject.service.IOrderService;
import com.epam.finalproject.service.IProductService;
import com.epam.finalproject.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editInvoice")
public class EditInvoiceServlet extends HttpServlet {
    private final IOrderService orderService = AppContext.getInstance().getOrderService();
    private final IUserService userService = AppContext.getInstance().getUserService();
    private final IProductService productService = AppContext.getInstance().getProductService();
    private final IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = String.valueOf(req.getSession().getAttribute("role"));
        Integer id = (Integer) req.getSession().getAttribute("id");

        if (role.equals("ADMIN") || role.equals("CASHIER") || role.equals("SENIOR_CASHIER")) {
            User user = userService.findById(id);
            req.setAttribute("user", user);
            if(req.getParameter("id")!=null) {

                Invoice invoice = invoiceService.findById(Integer.parseInt(req.getParameter("id")));
                int page = 1;
                int recordsPerPage = 5;
                if(req.getParameter("page") != null)
                    page = Integer.parseInt(req.getParameter("page"));
                req.getSession().setAttribute("path", req.getRequestURI() + "?id=" + Integer.parseInt(req.getParameter("id"))+"&page="+page);
                int noOfRecords = orderService.getNumberOfRows(invoice.getInvoiceCode());
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                req.setAttribute("noOfPages", noOfPages);
                req.setAttribute("currentPage", page);
                req.setAttribute("orders", orderService.findOrdersUsingLimitAndOffset(page,recordsPerPage,invoice.getInvoiceCode()));
                req.setAttribute("products", productService.findAll());
                req.setAttribute("invoiceId", Integer.parseInt(req.getParameter("id")));
                req.setAttribute("status", invoice.getStatus());
                req.setAttribute("total", orderService.totalInvoice(invoice.getInvoiceCode()));
                if(req.getParameter("isValidSet")!=null){
                    req.setAttribute("isValidSet", req.getParameter("isValidSet"));
                }
                else if(req.getParameter("isValid")!=null){
                    req.setAttribute("isValid", req.getParameter("isValid"));
                }
                else
                    req.setAttribute("isNull", req.getParameter("isNull"));
            }
            req.getRequestDispatcher("WEB-INF/admin/editInvoices.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }

    /**
     * take param orderAction and split to array
     * first index is action
     * second index is orderId
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String pathRedirect = req.getSession().getAttribute("path").toString();
        String [] orderAction = req.getParameter("orderAction").split("!");
        if(orderAction[1].equals("deleteOrder"))
            orderService.deleteOrder(orderAction[0]);
        if(orderAction[1].equals("updateOrder")) {
            Order order = orderService.findById(Integer.parseInt(orderAction[0]));
            Double quantity = Double.valueOf(req.getParameter("setQuantity"));
            Double checkQuantity = quantity -order.getQuantity();
            if(quantity==0)
                orderService.deleteOrder(order.getOrderId().toString());
            else if(orderService.isValidQuantity(checkQuantity,order.getProductCode()))
                orderService.updateQuantityOrder(order, quantity);
            else
                pathRedirect = pathRedirect+"&isValidSet="+order.getOrderId();
        }
        if(orderAction[1].equals("addOrder")){
            Double quantity =Double.valueOf(req.getParameter("addQuantity"));
            Order order = new Order();
            Invoice invoice = invoiceService.findById(Integer.parseInt(orderAction[0]));
            order.setInvoiceCode(invoice.getInvoiceCode());
            order.setProductCode(req.getParameter("productCode"));
            order.setQuantity(Double.valueOf(req.getParameter("addQuantity")));
            if(quantity==0)
                pathRedirect = pathRedirect+"&isNull=true";
            else if(orderService.isValidQuantity(quantity,order.getProductCode())) {
                order.setOrderValue(orderService.calculateCost(req.getParameter("productCode"), quantity));
                orderService.create(order);
            }
            else
                pathRedirect = pathRedirect+"&isValid=true";
        }
        resp.sendRedirect(pathRedirect);
    }
}
