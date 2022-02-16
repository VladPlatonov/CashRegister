package com.epam.finalproject.dao;


import com.epam.finalproject.model.Invoice;

import java.util.List;

public interface InvoiceDao {
    void create(Invoice invoice);
    Invoice getById(int id);
    void deleteByCode(Long code);
    void update(Invoice Invoice);
    void deleteById(int id);
    List<Invoice> findAll();
    List<Invoice> findInvoicesUsingLimitAndOffset(int currentPage, int numOfRecords);
    int getNumberOfRows();
}
