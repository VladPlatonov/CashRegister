package com.epam.finalproject.web.controller;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.model.Product;
import com.epam.finalproject.model.User;
import com.epam.finalproject.service.IProductService;
import com.epam.finalproject.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private final IUserService userService = AppContext.getInstance().getUserService();
    private final IProductService productService = AppContext.getInstance().getProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = String.valueOf(req.getSession().getAttribute("role"));
        Integer id = (Integer) req.getSession().getAttribute("id");

        if (role.equals("ADMIN") || role.equals("MERCHANT")) {
            int page = 1;
            int recordsPerPage = 5;
            if(req.getParameter("page") != null)
                page = Integer.parseInt(req.getParameter("page"));
            int noOfRecords = productService.getNumberOfRows();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            User user = userService.findById(id);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("products",productService.findProductsUsingLimitAndOffset(page,recordsPerPage));
            req.setAttribute("user", user);
            req.getRequestDispatcher("WEB-INF/admin/products.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }

    /**
     * take param productAction and split to array
     * first index is action
     * second index is productId
     */

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String [] productAction = req.getParameter("productAction").split("!");
        switch (productAction[1]){
            case "deleteProduct":  productService.deleteProduct(productService.findById(Integer.parseInt(productAction[0]))); break;
            case "add": req.getRequestDispatcher("WEB-INF/admin/addProduct.jsp").forward(req, resp);
            case "new": {
                Product product = new Product();
                product.setQuantity(Integer.parseInt(req.getParameter("quantity")));
                product.setCost(Integer.parseInt(req.getParameter("cost")));
                product.setName(req.getParameter("name"));
                product.setCode(req.getParameter("code"));
                product.setDescription(req.getParameter("description"));
                if(!productService.isNotNull(product)) {
                    req.setAttribute("isValid", "null");
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("WEB-INF/admin/addProduct.jsp").forward(req, resp);
                }
                if(productService.isValidProductCode(product.getCode()))
                    productService.create(product);
                else{
                    req.setAttribute("isValid","true");
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("WEB-INF/admin/addProduct.jsp").forward(req,resp);
                }
            }break;
            case "editProduct":{
                Product product = productService.findById(Integer.parseInt(productAction[0]));
                req.setAttribute("product", product);
                req.getRequestDispatcher("WEB-INF/admin/editProduct.jsp").forward(req, resp);
            } break;
            case "editProduct_update": {
                Product product = productService.findById(Integer.parseInt(req.getParameter("id")));
                product.setQuantity(Integer.parseInt(req.getParameter("quantity")));
                product.setCost(Integer.parseInt(req.getParameter("cost")));
                product.setName(req.getParameter("name"));
                product.setCode(req.getParameter("code"));
                product.setDescription(req.getParameter("description"));
                productService.update(product);
            }break;
        }
        resp.sendRedirect("/products");
    }
}
