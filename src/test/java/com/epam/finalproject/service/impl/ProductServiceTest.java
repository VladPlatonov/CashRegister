package com.epam.finalproject.service.impl;

import com.epam.finalproject.dao.impl.ProductDaoImpl;
import com.epam.finalproject.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    ProductDaoImpl productDao =  mock(ProductDaoImpl.class);

    Product product = new Product();

    @Before
    public void setUp() {
        product.setDescription("de");
        product.setName("d");
        product.setCost(100);
        product.setId(1);
        product.setQuantity(100);
        product.setCode("DE");
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testFindById() {
        when(productDao.getById(1)).thenReturn(product);
        assertEquals(product, productService.findById(1));
    }
}