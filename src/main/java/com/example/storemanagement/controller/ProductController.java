package com.example.storemanagement.controller;

import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Product;
import com.example.storemanagement.service.ProductService;
import com.example.storemanagement.service.impl.ProductServiceImpl;

import java.util.List;

public class ProductController {

    // service xử lý logic Product
    private ProductService service = new ProductServiceImpl();

    // lấy tất cả sản phẩm
    // dùng: productController.getAll();
    public List<Product> getAll() {
        return service.getAll();
    }

    // thêm sản phẩm mới
    // dùng: productController.add("Coca", 10000, 1);
    public void add(String name, double price, int categoryId) {
        service.add(name, price, categoryId);
    }

    // cập nhật sản phẩm theo id
    // dùng: productController.update(1, "Pepsi", 12000, 2);
    public void update(int id, String name, double price, int categoryId) {
        service.update(id, name, price, categoryId);
    }

    // xóa sản phẩm theo id
    // dùng: productController.delete(1);
    public void delete(int id) {
        service.delete(id);
    }

    // tìm sản phẩm theo id
    // dùng: productController.findById(1);
    public Product findById(int id) {
        return service.findById(id);
    }

    // tìm kiếm sản phẩm theo từ khóa
    // dùng: productController.search("coca");
    public List<Product> search(String keyword) {
        return service.search(keyword);
    }

    // lấy tồn kho sản phẩm theo chi nhánh
    // dùng: productController.getProductStockByBranch(1);
    public List<ProductStockDTO> getProductStockByBranch(Integer branchId) {
        return service.getProductStockByBranch(branchId);
    }
}