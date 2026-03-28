package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Supplier;
import java.util.List;

public interface SupplierService {
    List<Supplier> getAll();
    void add(Supplier s);
    void update(Supplier s);
    void delete(int id);
}