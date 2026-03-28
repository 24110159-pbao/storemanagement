package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.ProductDAO;
import com.example.storemanagement.dao.CategoryDAO;
import com.example.storemanagement.dao.impl.ProductDAOImpl;
import com.example.storemanagement.dao.impl.CategoryDAOImpl;
import com.example.storemanagement.model.entity.Product;
import com.example.storemanagement.model.entity.Category;
import com.example.storemanagement.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO = new ProductDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    @Override
    public void add(String name, double price, int categoryId) {
        Category c = categoryDAO.findById(categoryId);

        Product p = new Product();
        p.setProductName(name);
        p.setUnitPrice(BigDecimal.valueOf(price));
        p.setCategory(c);
        p.setStatus(false);

        productDAO.save(p);
    }

    @Override
    public void update(int id, String name, double price, int categoryId) {
        Product p = productDAO.findById(id);
        if (p == null) return;

        Category c = categoryDAO.findById(categoryId);

        p.setProductName(name);
        p.setUnitPrice(BigDecimal.valueOf(price));
        p.setCategory(c);

        productDAO.update(p);
    }

    @Override
    public void delete(int id) {
        productDAO.delete(id);
    }
}