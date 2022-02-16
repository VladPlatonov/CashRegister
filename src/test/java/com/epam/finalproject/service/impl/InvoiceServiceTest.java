package com.epam.finalproject.service.impl;

import com.epam.finalproject.dao.impl.InvoiceDaoImpl;
import com.epam.finalproject.model.Invoice;
import com.epam.finalproject.model.InvoiceStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;



public class InvoiceServiceTest {

    @Mock
    private InvoiceDaoImpl dao;


    @InjectMocks
    InvoiceService cut;

    Invoice testInvoice = new Invoice();


    @Before
    public void setUp(){
        testInvoice.setInvoiceId(1);
        testInvoice.setInvoiceCode(111L);
        testInvoice.setInvoiceNotes("Create");
        testInvoice.setStatus(InvoiceStatus.CREATED);
        testInvoice.setDate(new Timestamp(System.currentTimeMillis()));
        testInvoice.setUserId(1);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void finishInvoice() {
        cut.finishInvoice(testInvoice);
        Assert.assertEquals(testInvoice.getStatus(),InvoiceStatus.FINISHED);
    }

    @Test
    public void cancelInvoice() {
        cut.cancelInvoice(testInvoice);
        Assert.assertEquals(testInvoice.getStatus(),InvoiceStatus.CANCELLED);
    }


    @Test
    public void findById() {
        Mockito.when(dao.getById(1)).thenReturn(testInvoice);
        Assert.assertEquals(testInvoice,cut.findById(1));
    }

    @Test
    public void update() {
        cut.update(testInvoice);
        Mockito.verify(dao,Mockito.times(1)).update(testInvoice);
    }



}