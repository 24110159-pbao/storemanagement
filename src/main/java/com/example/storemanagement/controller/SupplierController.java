package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Supplier;
import com.example.storemanagement.service.SupplierService;
import com.example.storemanagement.service.impl.SupplierServiceImpl;

import java.util.List;

public class SupplierController {

    private final SupplierService service = new SupplierServiceImpl();

    public List<Supplier> getAllSuppliers() {
        return service.getAll();
    }

    public void addSupplier(String name, String address, String phone) {
        Supplier s = new Supplier();
        s.setSupplierName(name);
        s.setAddress(address);
        s.setPhone(phone);
        service.add(s);
    }

    public void updateSupplier(int id, String name, String address, String phone) {
        Supplier s = new Supplier();
        s.setSupplierID(id);
        s.setSupplierName(name);
        s.setAddress(address);
        s.setPhone(phone);
        service.update(s);
    }

    public void deleteSupplier(int id) {
        service.delete(id);
    }
}