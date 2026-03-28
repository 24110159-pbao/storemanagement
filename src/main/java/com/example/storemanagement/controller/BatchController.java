package com.example.storemanagement.controller;

import com.example.storemanagement.dao.BatchDAO;
import com.example.storemanagement.dao.BranchProductDAO;
import com.example.storemanagement.dao.impl.BatchDAOImpl;
import com.example.storemanagement.dao.impl.BranchDAOImpl;
import com.example.storemanagement.dao.impl.BranchProductDAOImpl;
import com.example.storemanagement.model.entity.*;
import com.example.storemanagement.service.BatchService;
import com.example.storemanagement.service.impl.BatchServiceImpl;

import java.util.Date;
import java.util.List;

public class BatchController {

    private BatchService service = new BatchServiceImpl();
    private BatchDAO batchDAO = new BatchDAOImpl();
    private BranchProductDAO branchProductDAO = new BranchProductDAOImpl();

    public List<Batch> getAll() {
        return service.getAll();
    }

    public void add(Product product, Supplier supplier, Date date, int quantity) {
        Batch b = new Batch();
        b.setProduct(product);
        b.setSupplier(supplier);
        b.setImportDate(date);
        b.setQuantity(quantity);

        service.add(b);
    }

    public void update(int id, Product product, Supplier supplier, Date date, int quantity) {
        Batch b = service.getById(id);
        if (b != null) {
            b.setProduct(product);
            b.setSupplier(supplier);
            b.setImportDate(date);
            b.setQuantity(quantity);

            service.update(b);
        }
    }

    public void delete(int batchId, Branch b) {

        Batch batch = batchDAO.findById(batchId);
        if (batch == null) return;

        Product p = batch.getProduct();
        int qty = batch.getQuantity();

        // 1. trừ tồn kho
        BranchProduct bp = branchProductDAO.findById(
                b.getBranchID(),
                p.getProductID()
        );

        if (bp != null) {
            int newQty = bp.getQuantity() - qty;

            if (newQty > 0) {
                bp.setQuantity(newQty);
                branchProductDAO.update(bp);
            } else {
                branchProductDAO.delete(bp);
            }
        }

        // 2. xóa batch
        batchDAO.delete(batchId);
    }
    public void add(Product p, Supplier s, Branch b, Date date, int qty) {

        // ===== 1. Lưu batch (KHÔNG cần branch) =====
        Batch batch = new Batch();
        batch.setProduct(p);
        batch.setSupplier(s);
        batch.setImportDate(date);
        batch.setQuantity(qty);


        batchDAO.save(batch);

        // ===== 2. Xử lý branch_products =====
        BranchProduct bp = branchProductDAO.findById(
                b.getBranchID(),
                p.getProductID()
        );

        if (bp == null) {
            // 👉 chưa có → insert
            bp = new BranchProduct();
            bp.setBranch(b);
            bp.setProduct(p);
            bp.setQuantity(qty);

            branchProductDAO.save(bp);
        } else {
            // 👉 đã có → cộng dồn
            bp.setQuantity(bp.getQuantity() + qty);
            branchProductDAO.update(bp);
        }
    }


}