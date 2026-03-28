package com.example.storemanagement.controller;
import com.example.storemanagement.model.entity.Category;
import com.example.storemanagement.service.CategoryService;
import com.example.storemanagement.service.impl.CategoryServiceImpl;

import java.util.List;

public class CategoryController {

    private CategoryService service = new CategoryServiceImpl();

    public List<Category> getAll() {
        return service.getAll();
    }

    public void add(String name) {
        Category c = new Category();
        c.setCategoryName(name);
        service.add(c);
    }

    public void update(int id, String name) {
        Category c = new Category();
        c.setCategoryID(id);
        c.setCategoryName(name);
        service.update(c);
    }

    public void delete(int id) {
        service.delete(id);
    }
}