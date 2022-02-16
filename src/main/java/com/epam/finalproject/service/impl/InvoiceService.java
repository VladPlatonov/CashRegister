package com.epam.finalproject.service.impl;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.dao.InvoiceDao;
import com.epam.finalproject.model.*;
import com.epam.finalproject.service.IInvoiceService;
import com.epam.finalproject.service.IOrderService;
import com.epam.finalproject.service.IProductService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


public class InvoiceService implements IInvoiceService {

    InvoiceDao invoiceDaoImpl = AppContext.getInstance().getInvoiceDao();
    IProductService productService = AppContext.getInstance().getProductService();
    IOrderService orderService = AppContext.getInstance().getOrderService();


    /**
     * Create invoice
     * @param products selected products to create
     * @param InvoiceCode generated code
     * @param user user who created
     * @return result of execution
     */
    @Override
    public boolean createInvoice(Map<String,Double> products, Long InvoiceCode, User user) {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Invoice invoice = new Invoice();
        boolean isCreate = false;
        invoice.setInvoiceCode(InvoiceCode);
        invoice.setUserId(user.getId());
        invoice.setStatus(InvoiceStatus.CREATED);
        invoice.setInvoiceNotes("CREATED BY:" + user.getUserRole().toString());
        invoice.setDate(stamp);
        invoiceDaoImpl.create(invoice);
        for (Map.Entry<String, Double> product: products.entrySet()) {
            Order order = new Order();
            order.setQuantity(product.getValue());
            if(product.getValue()==0) {
                isCreate = false;
                invoiceDaoImpl.deleteByCode(invoice.getInvoiceCode());
                break;
            }
            else if(productService.findByCode(product.getKey()).getQuantity()>order.getQuantity()) {
                order.setInvoiceCode(InvoiceCode);
                order.setProductCode(product.getKey());
                updateQuantity(product.getKey(),product.getValue());
                order.setOrderValue(orderService.calculateCost(product.getKey(), product.getValue()));
                orderService.create(order);
                isCreate=true;
            }
        }
        return isCreate;
    }


    /**
     * After update order
     * Update quantity product in warehouse
     * @param code product code
     * @param quantity quantity of products to change
     */
    private void updateQuantity(String code,Double quantity){
        Product product = productService.findByCode(code);
        Double tempQuantity = product.getQuantity()-quantity;
        productService.updateQuantity(code,tempQuantity);
    }


    @Override
    public void finishInvoice(Invoice invoice) {
        invoice.setStatus(InvoiceStatus.FINISHED);
        invoiceDaoImpl.update(invoice);
    }

    /**
     * After cancelled invoice
     * Update quantity products in warehouse
     * @param invoice
     */
    @Override
    public void cancelInvoice(Invoice invoice) {
        orderService.findAllByInvoiceCode(invoice.getInvoiceCode())
                .forEach(p->{
                   Product product = productService.findByCode(p.getProductCode());
                   product.setQuantity(product.getQuantity()+p.getQuantity());
                   productService.update(product);
                });
        invoice.setStatus(InvoiceStatus.CANCELLED);
        invoiceDaoImpl.update(invoice);
    }

    /**
     * Delete invoice and orders
     * @param invoice
     */
    public void deleteInvoice(Invoice invoice) {
        orderService.findAllByInvoiceCode(invoice.getInvoiceCode()).forEach(p->
            orderService.deleteById(p.getOrderId()));
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
