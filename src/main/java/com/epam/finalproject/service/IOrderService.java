package com.epam.finalproject.service;

import com.epam.finalproject.model.Order;

import java.util.List;

public interface IOrderService {
    void updateQuantityOrder(Order order, Double quantity);
    void deleteOrder(String orderId);
    boolean isValidQuantity(Double quantity,String productCode);
    void create(Order order);
    Order findById(int id);
    void update(Order order);
    Double calculateCost(String code,Double quantity);
    void deleteById(int id);
    List<Order> findAllByInvoiceCode(Long invoiceCode);
    List<Order> findAllByProductCode(String productCode);
    List<Order> findOrdersUsingLimitAndOffset(int currentPage, int recordsPerPage, Long orderCode);
    int getNumberOfRows(Long invoiceCode);
    Double totalInvoice(Long code);
}
