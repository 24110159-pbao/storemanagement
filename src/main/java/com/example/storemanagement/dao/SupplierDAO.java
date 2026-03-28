package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Supplier;
import java.util.List;

public interface SupplierDAO {
    void save(Supplier supplier);
    void update(Supplier supplier);
    void delete(Integer id);
    Supplier findById(Integer id);
    List<Supplier> findAll();
    List<Supplier> findByName(String name);
}