package com.epam.finalproject.context;

import com.epam.finalproject.dao.InvoiceDao;
import com.epam.finalproject.dao.OrderDao;
import com.epam.finalproject.dao.ProductDao;
import com.epam.finalproject.dao.UserDao;
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

    private InvoiceDao invoiceDao;
    private OrderDao orderDao;
    private ProductDao productDao;
    private UserDao userDao;



    /**
     * Service
     */
    private   IOrderService orderService;
    private   IProductService productService;
    private   IInvoiceService invoiceService;
    private   IUserService userService;


    /**
     * Double-Checked Locking
     */

    public static AppContext getInstance(){
        if (instance==null)
            synchronized (AppContext.class){
                if(instance ==null)
                    instance = new AppContext();
                    instance.getInvoiceService();
                    instance.getOrderService();
                    instance.getProductService();
                    instance.getUserService();
            }
        return instance;
    }

    private AppContext(){

    }

    public  UserDao getUserDao(){
        userDao = new UserDaoImpl();
        return userDao;
    }

    public  OrderDao getOrderDao(){
        orderDao = new OrderDaoImpl();
        return orderDao;
    }

    public  ProductDao getProductDao(){
        productDao = new ProductDaoImpl();
        return productDao;
    }

    public InvoiceDao getInvoiceDao(){
        invoiceDao = new InvoiceDaoImpl();
        return invoiceDao;
    }

    public  IUserService getUserService(){
        userService = new UserService();
        return userService;
    }
    public  IOrderService getOrderService(){
        orderService = new OrderService();
        return orderService;
    }
    public  IProductService getProductService(){
        productService = new ProductService();
        return productService;
    }
    public  IInvoiceService getInvoiceService(){
        invoiceService = new InvoiceService();
        return invoiceService;
    }
}
