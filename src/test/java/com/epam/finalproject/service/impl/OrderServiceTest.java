package com.epam.finalproject.service.impl;

import com.epam.finalproject.dao.impl.OrderDaoImpl;
import com.epam.finalproject.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    OrderDaoImpl orderDao =  mock(OrderDaoImpl.class);

    Order order = new Order();

    @Before
    public void setUp()  {
        order.setOrderId(1);
        order.setOrderValue(10);
        order.setQuantity(100);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        when(orderDao.getById(1)).thenReturn(order);
        assertEquals(order, orderService.findById(1));
    }
}