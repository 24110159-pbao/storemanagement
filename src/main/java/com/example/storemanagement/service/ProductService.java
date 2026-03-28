package com.example.storemanagement.service;

import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAll();
    void add(String name, double price, int categoryId);
    void update(int id, String name, double price, int categoryId);
    void delete(int id);
    Product findById(int id);
    List<Product> search(String keyword);
    List<ProductStockDTO> getProductStockByBranch(Integer branchId);
}