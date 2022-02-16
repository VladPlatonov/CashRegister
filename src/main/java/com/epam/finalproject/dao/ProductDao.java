package com.epam.finalproject.dao;

import com.epam.finalproject.model.Product;

import java.util.List;

public interface ProductDao {
    void create(Product product);
    Product getById(int id);
    Product getByCode(String code);
    void update(Product product);
    void deleteById(int id);
    void updateQuantity(String code,Double quantity);
    List<Product> findAll();
    List<Product> findProductsUsingLimitAndOffset(int currentPage, int numOfRecords);
    int getNumberOfRows();
}
