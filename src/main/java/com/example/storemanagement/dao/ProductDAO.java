package com.example.storemanagement.dao;

import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Product;
import java.util.List;

public interface ProductDAO {
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    void delete(int id);
    Product findById(int id);
    List<Product> search(String keyword);
    List<ProductStockDTO> getProductStockByBranch(Integer branchId);
}