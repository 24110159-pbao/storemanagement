package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Category;
import com.example.storemanagement.service.CategoryService;
import com.example.storemanagement.service.impl.CategoryServiceImpl;

import java.util.List;

public class CategoryController {

    // service xử lý logic Category
    private CategoryService service = new CategoryServiceImpl();

    // lấy tất cả danh mục
    // dùng: categoryController.getAll();
    public List<Category> getAll() {
        return service.getAll();
    }

    // thêm danh mục mới
    // dùng: categoryController.add("Đồ uống");
    public void add(String name) {
        Category c = new Category();
        c.setCategoryName(name);
        service.add(c);
    }

    // cập nhật danh mục theo id
    // dùng: categoryController.update(1, "Đồ ăn");
    public void update(int id, String name) {
        Category c = new Category();
        c.setCategoryID(id);
        c.setCategoryName(name);
        service.update(c);
    }

    // xóa danh mục theo id
    // dùng: categoryController.delete(1);
    public void delete(int id) {
        service.delete(id);
    }
}