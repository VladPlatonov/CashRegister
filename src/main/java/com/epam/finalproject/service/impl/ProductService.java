package com.epam.finalproject.service.impl;

import com.epam.finalproject.context.AppContext;
import com.epam.finalproject.dao.ConnectionPool;
import com.epam.finalproject.dao.ProductDao;
import com.epam.finalproject.model.Product;
import com.epam.finalproject.service.IProductService;

import java.sql.Connection;
import java.util.List;

public class ProductService implements IProductService {
    ProductDao productDaoImpl = AppContext.getInstance().getProductDao();
    private Connection connection = ConnectionPool.getInstance().getConnection();


    public boolean isValidProductCode(String code){
        return productDaoImpl.getByCode(code) == null;
    }

    public void deleteProduct(Product product){
        productDaoImpl.deleteById(product.getId());
    }

    @Override
    public void create(Product product) {
        productDaoImpl.create(product);
    }

    @Override
    public Product findById(int id) {
        return productDaoImpl.getById(id);
    }

    @Override
    public Product findByCode(String code) {
        return productDaoImpl.getByCode(code);
    }

    @Override
    public List<Product> findAll() {
        return productDaoImpl.findAll();
    }

    @Override
    public void update(Product product) {
        productDaoImpl.update(product);
    }

    @Override
    public void deleteById(int id) {
        productDaoImpl.deleteById(id);
    }

    @Override
    public void updateQuantity(String code, Integer quantity) {
        productDaoImpl.updateQuantity(code,quantity);
    }

    @Override
    public List<Product> findProductsUsingLimitAndOffset(int currentPage, int recordsPerPage) {
        return productDaoImpl.findProductsUsingLimitAndOffset(currentPage,recordsPerPage);
    }

    @Override
    public int getNumberOfRows() {
        return productDaoImpl.getNumberOfRows();
    }

    @Override
    public boolean isNotNull(Product product) {
        return product.getCode()!=null&&product.getQuantity()!=null&&product.getDescription()!=null
                &&product.getName()!=null&&product.getCost()!=null;
    }
}
