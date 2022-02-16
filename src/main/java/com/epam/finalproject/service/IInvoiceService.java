package com.epam.finalproject.service;

import com.epam.finalproject.model.Invoice;
import com.epam.finalproject.model.User;

import java.util.List;
import java.util.Map;

public interface IInvoiceService {
    boolean createInvoice(Map<String,Double> products, Long orderCode, User user);
    void finishInvoice(Invoice invoice);
    void cancelInvoice(Invoice invoice);
    void deleteInvoice(Invoice invoice);
    void create(Invoice invoice);
    Invoice findById(int id);
    void update(Invoice invoice);
    List<Invoice> findInvoicesUsingLimitAndOffset(int currentPage, int recordsPage);
    int getNumberOfRows();
}
