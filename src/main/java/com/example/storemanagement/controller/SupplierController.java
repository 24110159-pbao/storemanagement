package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Supplier;
import com.example.storemanagement.service.SupplierService;
import com.example.storemanagement.service.impl.SupplierServiceImpl;

import java.util.List;

public class SupplierController {

    // service xử lý logic Supplier
    private final SupplierService service = new SupplierServiceImpl();

    // lấy tất cả nhà cung cấp
    // dùng: supplierController.getAllSuppliers();
    public List<Supplier> getAllSuppliers() {
        return service.getAll();
    }

    // thêm nhà cung cấp mới
    // dùng: supplierController.addSupplier("Công ty A", "HCM", "0123456789");
    public void addSupplier(String name, String address, String phone) {
        Supplier s = new Supplier();
        s.setSupplierName(name);
        s.setAddress(address);
        s.setPhone(phone);
        service.add(s);
    }

    // cập nhật nhà cung cấp theo id
    // dùng: supplierController.updateSupplier(1, "Công ty B", "HN", "0987654321");
    public void updateSupplier(int id, String name, String address, String phone) {
        Supplier s = new Supplier();
        s.setSupplierID(id);
        s.setSupplierName(name);
        s.setAddress(address);
        s.setPhone(phone);
        service.update(s);
    }

    // xóa nhà cung cấp theo id
    // dùng: supplierController.deleteSupplier(1);
    public void deleteSupplier(int id) {
        service.delete(id);
    }
}