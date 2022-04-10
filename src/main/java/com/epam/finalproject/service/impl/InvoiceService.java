package com.epam.finalproject.service.impl;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.dao.ConnectionPool;
import com.epam.finalproject.dao.InvoiceDao;
import com.epam.finalproject.dao.OrderDao;
import com.epam.finalproject.dao.ProductDao;
import com.epam.finalproject.dao.exception.ServiceException;
import com.epam.finalproject.model.*;
import com.epam.finalproject.service.IInvoiceService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class InvoiceService implements IInvoiceService  {

    private Connection connection = ConnectionPool.getInstance().getConnection();

    InvoiceDao invoiceDaoImpl = AppContext.getInstance().getInvoiceDao();
    ProductDao productDaoImpl = AppContext.getInstance().getProductDao();
    OrderDao orderDaoImpl = AppContext.getInstance().getOrderDao();



    /**
     * Create invoice
     * @param products selected products to create
     * @param InvoiceCode generated code
     * @param user user who created
     * @return result of execution
     */
    @Override
    public boolean createInvoice(Map<String,Integer> products, Long InvoiceCode, User user){
        boolean isCreate = false;
        try {
            connection.setAutoCommit(false);
            Invoice invoice = new Invoice();
            invoice.setInvoiceCode(InvoiceCode);
            invoice.setUserId(user.getId());
            invoice.setStatus(InvoiceStatus.CREATED);
            invoice.setInvoiceNotes("CREATED BY:" + user.getUserRole().toString());
            invoice.setDate(LocalDateTime.now());
            invoiceDaoImpl.create(invoice);
            for (Map.Entry<String, Integer> selected: products.entrySet()) {
                Order order = new Order();
                Product product = productDaoImpl.getByCode(selected.getKey());
                order.setQuantity(selected.getValue());
                if(selected.getValue() == null) {
                    isCreate = false;
                    invoiceDaoImpl.deleteByCode(invoice.getInvoiceCode());
                    break;
                }
                else if(product.getQuantity()>=order.getQuantity()) {
                    order.setInvoiceCode(InvoiceCode);
                    order.setProductCode(selected.getKey());
                    productDaoImpl.updateQuantity(product.getCode(),product.getQuantity()- selected.getValue());
                    order.setOrderValue(selected.getValue()*product.getCost());
                    orderDaoImpl.create(order);
                    isCreate=true;
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw  new ServiceException("can't create invoice");
        }
        return isCreate;

    }


    @Override
    public void finishInvoice(Invoice invoice)  {
        invoice.setStatus(InvoiceStatus.FINISHED);
        invoiceDaoImpl.update(invoice);
    }

    /**
     * After cancelled invoice
     * Update quantity products in warehouse
     * @param invoice
     */
    @Override
    public void cancelInvoice(Invoice invoice){
        try {
            connection.setAutoCommit(false);
        orderDaoImpl.findAllByInvoiceCode(invoice.getInvoiceCode())
                .forEach(p->{
                   Product product = productDaoImpl.getByCode(p.getProductCode());
                   product.setQuantity(product.getQuantity()+p.getQuantity());
                   productDaoImpl.update(product);
                });
        invoice.setStatus(InvoiceStatus.CANCELLED);
        invoiceDaoImpl.update(invoice);
        connection.commit();
        } catch (SQLException e) {
            throw  new ServiceException("can't cancel invoice");
        }
    }

    /**
     * Delete invoice and orders
     * @param invoice
     */
    public void deleteInvoice(Invoice invoice)  {
        invoiceDaoImpl.deleteById(invoice.getInvoiceId());
    }

    @Override
    public void create(Invoice invoice) {
        invoiceDaoImpl.create(invoice);
    }

    @Override
    public Invoice findById(int id) {
        return invoiceDaoImpl.getById(id);
    }

    @Override
    public void update(Invoice invoice) {
        invoiceDaoImpl.update(invoice);
    }



    @Override
    public List<Invoice> findInvoicesUsingLimitAndOffset(int currentPage, int recordsPage) {
        return invoiceDaoImpl.findInvoicesUsingLimitAndOffset(currentPage,recordsPage);
    }

    @Override
    public int getNumberOfRows() {
        return invoiceDaoImpl.getNumberOfRows();
    }

}
