package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Supplier;
import java.util.List;

public interface SupplierDAO {

    // lấy tất cả nhà cung cấp
    // dùng: List<Supplier> list = supplierDAO.findAll();
    List<Supplier> findAll();

    // tìm nhà cung cấp theo id
    // dùng: Supplier s = supplierDAO.findById(1);
    Supplier findById(int id);

    // lưu nhà cung cấp mới vào DB
    // dùng: supplierDAO.save(supplier);
    void save(Supplier supplier);

    // cập nhật nhà cung cấp đã tồn tại
    // dùng: supplierDAO.update(supplier);
    void update(Supplier supplier);

    // xóa nhà cung cấp theo id
    // dùng: supplierDAO.delete(1);
    void delete(int id);
}