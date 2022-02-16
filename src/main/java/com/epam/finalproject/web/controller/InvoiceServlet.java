package com.epam.finalproject.web.controller;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.model.Invoice;
import com.epam.finalproject.model.InvoiceStatus;
import com.epam.finalproject.model.User;
import com.epam.finalproject.service.IInvoiceService;
import com.epam.finalproject.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/invoices")
public class InvoiceServlet extends HttpServlet {
    private final IUserService userService = AppContext.getInstance().getUserService();
    private final IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = String.valueOf(req.getSession().getAttribute("role"));
        Integer id = (Integer) req.getSession().getAttribute("id");

        if (role.equals("ADMIN")|| role.equals("CASHIER") || role.equals("SENIOR_CASHIER")) {
            User user = userService.findById(id);
            int page = 1;
            int recordsPerPage = 5;
            if(req.getParameter("page") != null)
                page = Integer.parseInt(req.getParameter("page"));
            int noOfRecords = invoiceService.getNumberOfRows();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("invoices",invoiceService.findInvoicesUsingLimitAndOffset(page,recordsPerPage));
            req.setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/admin/invoices.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
    /**
     * take param invoiceAction and split to array
     * first index is action
     * second index is invoiceId
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String [] invoiceAction = req.getParameter("invoiceAction").split("!");
        Invoice invoice = invoiceService.findById(Integer.parseInt(invoiceAction[0]));
        if(invoiceAction[1].equals("finishInvoice")){
            invoice.setStatus(InvoiceStatus.FINISHED);
            invoiceService.finishInvoice(invoice);
        }
        if(invoiceAction[1].equals("cancelInvoice"))
            invoiceService.cancelInvoice(invoice);
        if(invoiceAction[1].equals("deleteInvoice"))
            invoiceService.deleteInvoice(invoice);
        resp.sendRedirect("/invoices");
    }
}
