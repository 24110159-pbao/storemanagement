package com.example.storemanagement.controller;

import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Product;
import com.example.storemanagement.service.ProductService;
import com.example.storemanagement.service.impl.ProductServiceImpl;

import java.util.List;

public class ProductController {

    private ProductService service = new ProductServiceImpl();

    public List<Product> getAll() {
        return service.getAll();
    }

    public void add(String name, double price, int categoryId) {
        service.add(name, price, categoryId);
    }

    public void update(int id, String name, double price, int categoryId) {
        service.update(id, name, price, categoryId);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public Product findById(int id) {
        return service.findById(id);
    }

    public List<Product> search(String keyword) {
        return service.search(keyword);
    }

    public List<ProductStockDTO> getProductStockByBranch(Integer branchId) {

        return service.getProductStockByBranch(branchId);
    }
}