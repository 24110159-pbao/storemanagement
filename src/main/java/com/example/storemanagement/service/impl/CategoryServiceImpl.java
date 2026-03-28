package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.CategoryDAO;
import com.example.storemanagement.dao.impl.CategoryDAOImpl;
import com.example.storemanagement.model.entity.Category;
import com.example.storemanagement.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO dao = new CategoryDAOImpl();

    @Override
    public List<Category> getAll() {
        return dao.findAll();
    }

    @Override
    public void add(Category c) {
        dao.save(c);
    }

    @Override
    public void update(Category c) {
        dao.update(c);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
