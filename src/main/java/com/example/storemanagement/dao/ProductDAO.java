package com.example.storemanagement.dao;

import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Product;
import java.util.List;

public interface ProductDAO {

    // lấy tất cả sản phẩm
    // dùng: List<Product> list = productDAO.findAll();
    List<Product> findAll();

    // lưu sản phẩm mới vào DB
    // dùng: productDAO.save(product);
    void save(Product product);

    // cập nhật sản phẩm đã tồn tại
    // dùng: productDAO.update(product);
    void update(Product product);

    // xóa sản phẩm theo id
    // dùng: productDAO.delete(1);
    void delete(int id);

    // tìm sản phẩm theo id
    // dùng: Product p = productDAO.findById(1);
    Product findById(int id);

    // tìm kiếm sản phẩm theo từ khóa
    // dùng: List<Product> results = productDAO.search("Coca");
    List<Product> search(String keyword);

    // lấy tồn kho sản phẩm theo chi nhánh
    // dùng: List<ProductStockDTO> stock = productDAO.getProductStockByBranch(1);
    List<ProductStockDTO> getProductStockByBranch(Integer branchId);
}