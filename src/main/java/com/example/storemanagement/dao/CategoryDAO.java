package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Category;
import java.util.List;

public interface CategoryDAO {

    // lấy tất cả danh mục
    // dùng: List<Category> list = categoryDAO.findAll();
    List<Category> findAll();

    // tìm danh mục theo id
    // dùng: Category c = categoryDAO.findById(1);
    Category findById(int id);

    // lưu danh mục mới vào DB
    // dùng: categoryDAO.save(category);
    void save(Category category);

    // cập nhật danh mục đã tồn tại
    // dùng: categoryDAO.update(category);
    void update(Category category);

    // xóa danh mục theo id
    // dùng: categoryDAO.delete(1);
    void delete(int id);
}