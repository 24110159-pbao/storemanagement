package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    void add(Category c);
    void update(Category c);
    void delete(int id);
}
