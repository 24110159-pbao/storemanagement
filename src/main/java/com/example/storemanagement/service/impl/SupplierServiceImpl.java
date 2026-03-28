package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.SupplierDAO;
import com.example.storemanagement.dao.impl.SupplierDAOImpl;
import com.example.storemanagement.model.entity.Supplier;
import com.example.storemanagement.service.SupplierService;

import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    private final SupplierDAO dao = new SupplierDAOImpl();

    @Override
    public List<Supplier> getAll() {
        return dao.findAll();
    }

    @Override
    public void add(Supplier s) {
        dao.save(s);
    }

    @Override
    public void update(Supplier s) {
        dao.update(s);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}