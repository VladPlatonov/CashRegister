package com.epam.finalproject.dao;


import com.epam.finalproject.model.Order;

import java.util.List;

public interface OrderDao {
    void create(Order order);
    Order getById(int id);
    void update(Order order);
    void deleteById(int id);
    List<Order> findAllByInvoiceCode(Long invoiceCode);
    List<Order> findAllByProductCode(String productCode);
    List<Order> findOrdersUsingLimitAndOffset(int currentPage, int recordsPerPage, Long invoiceCode);
    int getNumberOfRows(Long invoiceCode);
}
