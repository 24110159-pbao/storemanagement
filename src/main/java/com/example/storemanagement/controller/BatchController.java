package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.*;
import com.example.storemanagement.service.BatchService;
import com.example.storemanagement.service.impl.BatchServiceImpl;

import java.util.Date;
import java.util.List;

public class BatchController {

    // service xử lý logic Batch
    private BatchService service = new BatchServiceImpl();

    // lấy tất cả batch
    // dùng: batchController.getAll();
    public List<Batch> getAll() {
        return service.getAll();
    }

    // thêm batch mới
    // dùng: batchController.add(product, supplier, branch, new Date(), 100);
    public void add(Product product, Supplier supplier, Branch branch, Date date, int quantity) {
        Batch b = new Batch();
        b.setProduct(product);
        b.setSupplier(supplier);
        b.setBranch(branch);
        b.setImportDate(date);
        b.setQuantity(quantity);

        service.add(b);
    }

    // cập nhật batch theo id
    // dùng: batchController.update(1, product, supplier, branch, new Date(), 200);
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

    // xóa batch theo id
    // dùng: batchController.delete(1);
    public void delete(int id) {
        service.delete(id);
    }
}