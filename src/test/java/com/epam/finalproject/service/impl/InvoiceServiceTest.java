package com.epam.finalproject.service.impl;

import com.epam.finalproject.dao.impl.InvoiceDaoImpl;
import com.epam.finalproject.model.Invoice;
import com.epam.finalproject.model.InvoiceStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class InvoiceServiceTest {

    @InjectMocks
    InvoiceService invoiceService;

    InvoiceDaoImpl invoiceDao = mock(InvoiceDaoImpl.class);

    Invoice invoice = new Invoice();

    @Before
    public void setUp(){
        invoice.setInvoiceId(1);
        invoice.setDate(LocalDateTime.now());
        invoice.setStatus(InvoiceStatus.CREATED);
        invoice.setInvoiceCode(2L);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        when(invoiceDao.getById(1)).thenReturn(invoice);
        assertEquals(invoice, invoiceService.findById(1));
    }

    @Test
    public void testDeleteInvoice() {
        invoiceService.deleteInvoice(invoice);
        verify(invoiceDao).deleteById(1);
    }


}