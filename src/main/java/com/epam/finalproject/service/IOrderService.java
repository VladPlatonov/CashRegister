package com.epam.finalproject.service;

import com.epam.finalproject.model.Order;

import java.util.List;

public interface IOrderService {
    boolean updateQuantityOrder(Order order, Integer quantity);
    void deleteOrder(String orderId);
    boolean create(Order order);
    Order findById(int id);
    void update(Order order);
    void deleteById(int id);
    List<Order> findOrdersUsingLimitAndOffset(int currentPage, int recordsPerPage, Long orderCode);
    int getNumberOfRows(Long invoiceCode);
    Integer totalInvoice(Long code);
}
