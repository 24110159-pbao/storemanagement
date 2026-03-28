package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Product;
import java.util.List;

public interface ProductDAO {
    void save(Product product);
    void update(Product product);
    void delete(Integer id);
    Product findById(Integer id);
    List<Product> findAll();
    List<Product> findByName(String name);
}