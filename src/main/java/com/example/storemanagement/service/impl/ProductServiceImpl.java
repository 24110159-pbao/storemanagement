package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.ProductDAO;
import com.example.storemanagement.model.entity.Product;
import com.example.storemanagement.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void create(Product product) {
        validate(product);
        productDAO.save(product);
    }

    @Override
    public void update(Product product) {
        if (product.getProductId() == null) {
            throw new IllegalArgumentException("Product ID must not be null for update");
        }
        validate(product);
        productDAO.update(product);
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }
        productDAO.delete(id);
    }

    @Override
    public Product getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }
        return productDAO.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    @Override
    public List<Product> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return productDAO.findAll();
        }
        return productDAO.findByName(name.trim());
    }

    // =========================
    // PRIVATE VALIDATION
    // =========================

    private void validate(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (product.getUnitPrice() == null || product.getUnitPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Unit price must be >= 0");
        }
    }
}