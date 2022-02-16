package com.epam.finalproject.context;

import com.epam.finalproject.dao.*;
import com.epam.finalproject.dao.impl.InvoiceDaoImpl;
import com.epam.finalproject.dao.impl.OrderDaoImpl;
import com.epam.finalproject.dao.impl.ProductDaoImpl;
import com.epam.finalproject.dao.impl.UserDaoImpl;
import com.epam.finalproject.service.IInvoiceService;
import com.epam.finalproject.service.IOrderService;
import com.epam.finalproject.service.IProductService;
import com.epam.finalproject.service.IUserService;
import com.epam.finalproject.service.impl.InvoiceService;
import com.epam.finalproject.service.impl.OrderService;
import com.epam.finalproject.service.impl.ProductService;
import com.epam.finalproject.service.impl.UserService;

/**
 * This is context WebApp
 * The class contains fields in which instances of services and dao
 * Used singleton
 */

public class AppContext {

    private static volatile AppContext instance;

    /**
     * Dao
     */

    private static final InvoiceDao invoiceDao;
    private static final OrderDao orderDao;
    private static final ProductDao productDao;
    private static final UserDao userDao;

    /**
     * Service
     */
    private static final IOrderService orderService;
    private static final IProductService productService;
    private static final IInvoiceService invoiceService;
    private static final IUserService userService;


    static{
        invoiceDao = new InvoiceDaoImpl();
        orderDao = new OrderDaoImpl();
        productDao = new ProductDaoImpl();
        userDao = new UserDaoImpl();
        orderService = new OrderService();
        productService = new ProductService();
        invoiceService = new InvoiceService();
        userService = new UserService();
    }

    /**
     * Double-Checked Locking
     */

    public static AppContext getInstance(){
        if (instance==null)
            synchronized (AppContext.class){
                if(instance ==null)
                    instance = new AppContext();
            }
        return instance;
    }

    private AppContext(){

    }

    public static UserDao getUserDao(){
        return userDao;
    }

    public static OrderDao getOrderDao(){
        return orderDao;
    }

    public static ProductDao getProductDao(){
        return productDao;
    }

    public static InvoiceDao getInvoiceDao(){
        return invoiceDao;
    }

    public static IUserService getUserService(){
        return userService;
    }
    public static IOrderService getOrderService(){
        return orderService;
    }
    public static IProductService getProductService(){
        return productService;
    }
    public static IInvoiceService getInvoiceService(){
        return invoiceService;
    }
}
