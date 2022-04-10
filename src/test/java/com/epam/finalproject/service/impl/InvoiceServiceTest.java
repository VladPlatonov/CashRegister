package com.epam.finalproject.service.impl;

import com.epam.finalproject.dao.impl.InvoiceDaoImpl;
import com.epam.finalproject.model.Invoice;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


public class InvoiceServiceTest {

    @Mock
    private InvoiceDaoImpl dao;


    @InjectMocks
    InvoiceService cut;

    Invoice testInvoice = new Invoice();




    @Test
    public void finishInvoice() {

    }




    @Test
    public void update() {

    }



}