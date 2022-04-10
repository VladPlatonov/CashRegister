package com.epam.finalproject.service.impl;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.dao.ConnectionPool;
import com.epam.finalproject.dao.OrderDao;
import com.epam.finalproject.dao.ProductDao;
import com.epam.finalproject.dao.exception.ServiceException;
import com.epam.finalproject.model.Order;
import com.epam.finalproject.model.Product;
import com.epam.finalproject.service.IOrderService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService implements IOrderService {

    private Connection connection = ConnectionPool.getInstance().getConnection();


    OrderDao orderDaoImpl = AppContext.getInstance().getOrderDao();
    ProductDao productDaoImpl = AppContext.getInstance().getProductDao();

    /**
     * update quantity order
     * If the number is increased the quantity product in the warehouse decreases
     * Else quantity product in the warehouse increases
     * @param order
     * @param quantity
     */
    @Override
    public boolean updateQuantityOrder(Order order, Integer quantity){
        boolean isCreate =false;
        try {
            connection.setAutoCommit(false);
            Product product = productDaoImpl.getByCode(order.getProductCode());
            if(product.getQuantity()>=quantity -order.getQuantity()) {
                order.setQuantity(quantity);
                productDaoImpl.updateQuantity(order.getProductCode(),product
                        .getQuantity()-quantity + order.getQuantity());
                order.setOrderValue(order.getQuantity()*product.getCost());
                orderDaoImpl.update(order);
                isCreate=true;
            }
            connection.commit();
        } catch (SQLException e) {
            throw  new ServiceException("can't update Order");
        }
    return isCreate;
    }
    public Integer totalInvoice(Long code){
    return orderDaoImpl.findAllByInvoiceCode(code).stream()
                        .map(Order::getOrderValue)
                        .mapToInt(Integer::intValue).sum();
    }

    @Override
    public void deleteOrder(String orderId){
        try {
            connection.setAutoCommit(false);
            Order order = orderDaoImpl.getById(Integer.parseInt(orderId));
            productDaoImpl.updateQuantity(order.getProductCode(),order.getQuantity() + productDaoImpl.getByCode(order.getProductCode()).getQuantity());
            orderDaoImpl.deleteById(order.getOrderId());
            connection.commit();
        } catch (SQLException e) {
            throw  new ServiceException("can't  delete Order");
        }
    }

    @Override
    public boolean create(Order order) {
        boolean isCreate = false;
        try {
            connection.setAutoCommit(false);
            Product product = productDaoImpl.getByCode(order.getProductCode());
            productDaoImpl.updateQuantity(order.getProductCode(),product.getQuantity()- order.getQuantity());
            order.setOrderValue(order.getQuantity()*product.getCost());
            if(product.getQuantity()>=order.getQuantity()) {
                orderDaoImpl.create(order);
                isCreate=true;
            }
            connection.commit();
        } catch (SQLException e) {
            throw  new ServiceException("can't  create Order");
        }
        return isCreate;
    }

    @Override
    public Order findById(int id) {
        return orderDaoImpl.getById(id);
    }

    @Override
    public void update(Order order) {
        orderDaoImpl.update(order);
    }

    @Override
    public void deleteById(int id) {
        orderDaoImpl.deleteById(id);
    }

    @Override
    public List<Order> findOrdersUsingLimitAndOffset(int currentPage, int recordsPerPage, Long invoiceCode) {
        return orderDaoImpl.findOrdersUsingLimitAndOffset(currentPage,recordsPerPage,invoiceCode);
    }

    @Override
    public int getNumberOfRows(Long invoiceCode) {
        return orderDaoImpl.getNumberOfRows(invoiceCode);
    }
}
