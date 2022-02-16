package com.epam.finalproject.service;

import com.epam.finalproject.model.Product;

import java.util.List;

public interface IProductService {
    boolean isValidProductCode(String code);
    void deleteProduct(Product product);
    void create(Product product);
    Product findById(int id);
    Product findByCode(String code);
    List<Product> findAll();
    void update(Product product);
    void deleteById(int id);
    void updateQuantity(String  code, Double quantity);
    List<Product> findProductsUsingLimitAndOffset(int currentPage, int recordsPerPage);
    int getNumberOfRows();
    boolean isNotNull(Product product);
}
