package com.epam.finalproject.service.impl;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.dao.OrderDao;
import com.epam.finalproject.dao.ProductDao;
import com.epam.finalproject.model.Order;
import com.epam.finalproject.service.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {
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
    public void updateQuantityOrder(Order order, Double quantity){
        Double tempQuantity;
        if(quantity > order.getQuantity()) {
           tempQuantity = quantity - order.getQuantity();
           productDaoImpl.updateQuantity(order.getProductCode(),
                   productDaoImpl.getByCode(order.getProductCode()).getQuantity()-tempQuantity);
        }
        else {
            tempQuantity = order.getQuantity() -quantity;
            productDaoImpl.updateQuantity(order.getProductCode(),
                    productDaoImpl.getByCode(order.getProductCode()).getQuantity()+tempQuantity);
        }
        order.setQuantity(quantity);
        order.setOrderValue(calculateCost(order.getProductCode(),quantity));
        orderDaoImpl.update(order);


    }
    public Double totalInvoice(Long code){
    return orderDaoImpl.findAllByInvoiceCode(code).stream()
                        .map(Order::getOrderValue)
                        .mapToDouble(Double::doubleValue).sum();
    }
    @Override
    public Double calculateCost(String code,Double quantity){
        return quantity*productDaoImpl.getByCode(code).getCost();
    }

    @Override
    public void deleteOrder(String orderId){
        Order order = orderDaoImpl.getById(Integer.parseInt(orderId));
        Double updateQuantity = order.getQuantity() + productDaoImpl.getByCode(order.getProductCode()).getQuantity();
        productDaoImpl.updateQuantity(order.getProductCode(),updateQuantity);
        orderDaoImpl.deleteById(order.getOrderId());
    }
    @Override
    public boolean isValidQuantity(Double quantity,String productCode){
        return productDaoImpl.getByCode(productCode).getQuantity()>=quantity;
    }

    @Override
    public void create(Order order) {
        orderDaoImpl.create(order);
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
    public List<Order> findAllByInvoiceCode(Long invoiceCode) {
        return orderDaoImpl.findAllByInvoiceCode(invoiceCode);
    }

    @Override
    public List<Order> findAllByProductCode(String productCode) {
        return orderDaoImpl.findAllByProductCode(productCode);
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
