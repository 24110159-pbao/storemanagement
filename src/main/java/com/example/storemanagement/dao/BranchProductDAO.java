package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.BranchProduct;

public interface BranchProductDAO {

    BranchProduct findByBranchAndProduct(int branchId, int productId);
    BranchProduct findById(int branchId, int productId);
    void save(BranchProduct bp);

    void update(BranchProduct bp);
    void delete(BranchProduct bp);
}