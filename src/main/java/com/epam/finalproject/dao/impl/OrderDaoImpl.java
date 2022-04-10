package com.epam.finalproject.dao.impl;

import com.epam.finalproject.dao.ConnectionPool;
import com.epam.finalproject.dao.OrderDao;
import com.epam.finalproject.dao.exception.DaoException;
import com.epam.finalproject.model.Order;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private  static final String SQL_CREATE_ORDER = "INSERT INTO orders (invoice_code,product_code, "+
            "order_quantity,order_value)" +
            "VALUES (?,?,?,?)";
    private static final String SQL_GET_BY_ID_ORDER = "SELECT * FROM orders WHERE order_id = ?";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET invoice_code = ?, product_code = ?," +
            "order_quantity = ? , order_value = ? WHERE order_id = ? ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM orders WHERE order_id = ? ";
    private static final String SQL_SELECT_ALL_BY_INVOICE_CODE = " SELECT * FROM orders WHERE invoice_code = ? ";
    private static final String SQL_SELECT_ALL_BY_PRODUCT_CODE = " SELECT * FROM orders WHERE product_code = ?";
    private static final String SQL_SELECT_OFFSET = "SELECT * FROM orders WHERE invoice_code = ? ORDER BY order_id LIMIT ? OFFSET ? ";
    private static final String SQL_SELECT_COUNT = "SELECT COUNT(order_id)  AS count FROM orders WHERE invoice_code = ?";
    private static final Logger log = Logger.getLogger(OrderDaoImpl.class);


    private Connection connection = ConnectionPool.getInstance().getConnection();

    @Override
    public void create(Order order) {
        log.info("Enter create order:\n" + order);
        try {
             PreparedStatement insertStatement = connection.prepareStatement(SQL_CREATE_ORDER);
            insertStatement.setLong(1,order.getInvoiceCode());
            insertStatement.setString(2,order.getProductCode());
            insertStatement.setInt(3,order.getQuantity());
            insertStatement.setInt(4,order.getOrderValue());
            insertStatement.execute();
        } catch (SQLException e) {
            log.error("Can`t create order");
            throw new DaoException("Can`t create order", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public Order getById(int id) {
        log.info("Enter getById orderId:" + id);
        Order order = null;
        try    {
             PreparedStatement insertStatement = connection.prepareStatement(SQL_GET_BY_ID_ORDER);
            insertStatement.setInt(1, id);
            ResultSet resultSet = insertStatement.executeQuery();
                while (resultSet.next()) {
                    order = new Order();
                    order.setOrderId(resultSet.getInt("order_id"));
                    order.setInvoiceCode(resultSet.getLong("invoice_code"));
                    order.setProductCode(resultSet.getString("product_code"));
                    order.setOrderValue(resultSet.getInt("order_value"));
                    order.setQuantity(resultSet.getInt("order_quantity"));
                }


        } catch (SQLException e) {
            log.error("Can`t get order");
            throw new DaoException("Can`t get order", e);
        }
        log.info("Done!\n=======");
        return order;
    }

    @Override
    public void update(Order payment) {
        log.info("Enter update order:\n " + payment);
        try {
                PreparedStatement insertStatement = connection.prepareStatement(SQL_UPDATE_ORDER);
                insertStatement.setLong(1, payment.getInvoiceCode());
                insertStatement.setString(2, payment.getProductCode());
                insertStatement.setInt(3, payment.getQuantity());
                insertStatement.setInt(4, payment.getOrderValue());
                insertStatement.setInt(5, payment.getOrderId());
                insertStatement.executeUpdate();
        }
            catch (SQLException e) {
                log.error("Can`t update order");
            throw new DaoException("Can`t update order", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public void deleteById(int id) {
        log.info("Enter deleteById order: " + id);
        try {
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can`t delete order");
            throw new DaoException("Can`t delete order", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public List<Order> findAllByInvoiceCode(Long invoiceCode) {
        log.info("Enter findAllByOrderCode invoiceCode: " + invoiceCode);
        List<Order> orders  = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BY_INVOICE_CODE);
            statement.setLong(1, invoiceCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setInvoiceCode(resultSet.getLong("invoice_code"));
                order.setProductCode(resultSet.getString("product_code"));
                order.setOrderValue(resultSet.getInt("order_value"));
                order.setQuantity(resultSet.getInt("order_quantity"));
                orders.add(order);
            }
        } catch (SQLException e) {
            log.error("Can`t find all orders by invoiceCode");
            throw new DaoException("Can`t find all orders by invoiceCode", e);
        }
        log.info("Done!\n=======");
        return orders;
    }

    @Override
    public List<Order> findAllByProductCode(String productCode) {
        log.info("Enter findAllByProductCode productCode:");
        List<Order> orders  = new ArrayList<>();
        try {
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BY_PRODUCT_CODE);
            statement.setString(1, productCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setInvoiceCode(resultSet.getLong("invoice_code"));
                order.setProductCode(resultSet.getString("product_code"));
                order.setOrderValue(resultSet.getInt("order_value"));
                order.setQuantity(resultSet.getInt("order_quantity"));
                orders.add(order);
            }
        } catch (SQLException e) {
            log.error("Can`t find all orders by productCode");
            throw new DaoException("Can`t find all orders by productCode", e);
        }
        log.info("Done!\n=======");
        return orders;
    }
    @Override
    public List<Order> findOrdersUsingLimitAndOffset(int currentPage, int recordsPerPage, Long invoiceCode) {
        log.info("Enter findOrdersUsingLimitAndOffset orders:");
        List<Order> orders = new ArrayList<>();
        int start = currentPage * recordsPerPage - recordsPerPage;
        try {
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_OFFSET);
            statement.setLong(1,invoiceCode);
            statement.setInt(2, recordsPerPage);
            statement.setInt(3, start);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setInvoiceCode(resultSet.getLong("invoice_code"));
                order.setProductCode(resultSet.getString("product_code"));
                order.setOrderValue(resultSet.getInt("order_value"));
                order.setQuantity(resultSet.getInt("order_quantity"));
                orders.add(order);
            }
        } catch (SQLException e) {
            log.info("Can`t find orders with filters");
            throw new DaoException("Can`t find orders with filters", e);
        }
        log.info("Done!\n=======");
        return orders;
    }

    @Override
    public int getNumberOfRows(Long orderCode) {
        log.info("Enter getNumberOfRows orders:");
        int numOfRows = 0;
        try {
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT);
            statement.setLong(1,orderCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numOfRows = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            log.error("Can`t get orders of rows orders");
            throw new DaoException("Can`t get orders of rows orders", e);
        }
        log.info("Done!\n=======");
        return numOfRows;
    }
}
