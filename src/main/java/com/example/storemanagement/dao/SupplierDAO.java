package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Supplier;
import java.util.List;

public interface SupplierDAO {
    List<Supplier> findAll();
    Supplier findById(int id);
    void save(Supplier supplier);
    void update(Supplier supplier);
    void delete(int id);
}