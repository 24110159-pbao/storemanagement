package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Product;
import java.util.List;

public interface ProductService {
    void create(Product product);
    void update(Product product);
    void delete(Integer id);
    Product getById(Integer id);
    List<Product> getAll();
    List<Product> searchByName(String name);
}