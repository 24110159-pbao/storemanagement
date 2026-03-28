package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Category;
import java.util.List;

public interface CategoryDAO {
    List<Category> findAll();
    Category findById(int id);
    void save(Category category);
    void update(Category category);
    void delete(int id);
}