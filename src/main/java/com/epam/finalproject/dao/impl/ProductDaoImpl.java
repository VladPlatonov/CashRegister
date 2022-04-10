package com.epam.finalproject.dao.impl;

import com.epam.finalproject.dao.ConnectionPool;
import com.epam.finalproject.dao.ProductDao;
import com.epam.finalproject.dao.exception.DaoException;
import com.epam.finalproject.model.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private static final String SQL_CREATE_PRODUCT ="INSERT INTO products (product_code, " +
            "product_name, product_description," +
            "product_cost, product_quantity" +
            ") VALUES (?,?,?,?,?);";
    private static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE product_id = ?";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE products SET product_code=?," +
            "product_name=?, product_description=?," +
            "product_cost=?, product_quantity=? " +
            "WHERE product_id=?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM products WHERE product_id = ? ";
    private static final String SQL_SELECT_ALL_PRODUCT = "SELECT * FROM products";
    private static final String SQL_SELECT_BY_CODE = "SELECT * FROM products WHERE  product_code = ?";
    private static final String SQL_UPDATE_PRODUCT_QUANTITY = "UPDATE products SET product_quantity = ? WHERE product_code = ?";
    private static final String SQL_SELECT_OFFSET = "SELECT * FROM products ORDER BY product_id LIMIT ? OFFSET ?";
    private static final String SQL_SELECT_COUNT = "SELECT COUNT(product_id) AS count FROM products";
    private static final Logger log = Logger.getLogger(ProductDaoImpl.class);

    private Connection connection = ConnectionPool.getInstance().getConnection();


    @Override
    public void create(Product product) {
        log.info("Enter create product: " + product);
        try {
             PreparedStatement insertStatement = connection.prepareStatement(SQL_CREATE_PRODUCT);
            insertStatement.setString(1, product.getCode());
            insertStatement.setString(2, product.getName());
            insertStatement.setString(3, product.getDescription());
            insertStatement.setInt(4, product.getCost());
            insertStatement.setInt(5, product.getQuantity());
            insertStatement.execute();
        } catch (SQLException e) {
            log.error("Can`t create product");
            throw new DaoException("Can`t create product", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public Product getById(int id) {
        log.info("Enter getById productId:" + id);
        Product product = null;
        try {
            PreparedStatement insertStatement = connection.prepareStatement(SQL_SELECT_PRODUCT_BY_ID);
            insertStatement.setInt(1, id);
            ResultSet resultSet = insertStatement.executeQuery();
                while (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getInt("product_id"));
                    product.setCode(resultSet.getString("product_code"));
                    product.setName(resultSet.getString("product_name"));
                    product.setDescription(resultSet.getString("product_description"));
                    product.setCost(resultSet.getInt("product_cost"));
                    product.setQuantity(resultSet.getInt("product_quantity"));
                }

        } catch (SQLException e) {
            log.error("Can`t get product");
            throw new DaoException("Can`t get product", e);
        }
        log.info("Done!\n=======");
        return product;
    }

    @Override
    public Product getByCode(String code) {
        log.info("Enter getByCode product: "+code);
        Product product = null;
        try {
            PreparedStatement insertStatement = connection.prepareStatement(SQL_SELECT_BY_CODE);
            insertStatement.setString(1, code);
            ResultSet resultSet = insertStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setCode(resultSet.getString("product_code"));
                product.setName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("product_description"));
                product.setCost(resultSet.getInt("product_cost"));
                product.setQuantity(resultSet.getInt("product_quantity"));
            }

        } catch (SQLException e) {
            log.error("Can`t get product");
            throw new DaoException("Can`t get product", e);
        }
        log.info("Done!\n=======");
        return product;
    }


    @Override
    public void update(Product product) {
        log.info("Enter update product: \n"+product);
        try {
            PreparedStatement insertStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT);
            insertStatement.setString(1, product.getCode());
            insertStatement.setString(2, product.getName());
            insertStatement.setString(3, product.getDescription());
            insertStatement.setInt(4, product.getCost());
            insertStatement.setInt(5, product.getQuantity());
            insertStatement.setInt(6, product.getId());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Can`t update product");
            throw new DaoException("Can`t update product", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public void deleteById(int id) {
        log.info("Enter deleteById productId: " + id);
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can`t delete product");
            throw new DaoException("Can`t delete product", e);
        }
        log.info("Done!\n=======");
    }

    @Override
    public void updateQuantity(String  code, Integer quantity) {
        log.info("Enter updateQuantity productCode:" + code);
        try {
            PreparedStatement insertStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT_QUANTITY);
                insertStatement.setInt(1,quantity);
                insertStatement.setString(2,code);
                insertStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can`t update product");
            throw new DaoException("Can`t update product", e);
        }
        log.info("Done!\n=======");
    }


    @Override
    public List<Product> findAll() {
        log.info("Enter findAll products:");
        List<Product> products  = new ArrayList<>();
        try {
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_PRODUCT);
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setCode(resultSet.getString("product_code"));
                product.setName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("product_description"));
                product.setCost(resultSet.getInt("product_cost"));
                product.setQuantity(resultSet.getInt("product_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            log.error("Can`t find all products");
            throw new DaoException("Can`t find all products", e);
        }
        log.info("Done!\n=======");
        return products;
    }

    @Override
    public List<Product> findProductsUsingLimitAndOffset(int currentPage, int recordsPerPage) {
        log.info("Enter findProductsUsingLimitAndOffset products:");
        List<Product> products  = new ArrayList<>();
        int start = (currentPage) * recordsPerPage - recordsPerPage;
        try {

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_OFFSET);
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, start);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setCode(resultSet.getString("product_code"));
                product.setName(resultSet.getString("product_name"));
                product.setDescription(resultSet.getString("product_description"));
                product.setCost(resultSet.getInt("product_cost"));
                product.setQuantity(resultSet.getInt("product_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            log.error("Can`t find user with filters");
            throw new DaoException("Can`t find products with filters", e);
        }
        log.info("Done!\n=======");
        return products;
    }

    @Override
    public int getNumberOfRows() {
        log.info("Enter getNumberOfRows products: ");
        int numOfRows = 0;
        try {
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numOfRows = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            log.error("Can`t get numbers of rows products");
            throw new DaoException("Can`t get numbers of rows products", e);
        }
        log.info("Done!\n=======");
        return numOfRows;
    }
}
