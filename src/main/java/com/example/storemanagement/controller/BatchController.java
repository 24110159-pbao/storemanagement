package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.*;
import com.example.storemanagement.service.BatchService;
import com.example.storemanagement.service.impl.BatchServiceImpl;

import java.util.Date;
import java.util.List;

public class BatchController {

    private BatchService service = new BatchServiceImpl();

    public List<Batch> getAll() {
        return service.getAll();
    }

    public void add(Product product, Supplier supplier, Branch branch, Date date, int quantity) {
        Batch b = new Batch();
        b.setProduct(product);
        b.setSupplier(supplier);
        b.setBranch(branch);
        b.setImportDate(date);
        b.setQuantity(quantity);

        service.add(b);
    }

    public void update(int id, Product product, Supplier supplier, Branch branch, Date date, int quantity) {
        Batch b = new Batch();
        b.setBatchID(id);
        b.setProduct(product);
        b.setSupplier(supplier);
        b.setBranch(branch);
        b.setImportDate(date);
        b.setQuantity(quantity);

        service.update(b);
    }

    public void delete(int id) {
        service.delete(id);
    }
}