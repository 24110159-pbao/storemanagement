package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.BranchDAO;
import com.example.storemanagement.model.entity.Branch;

public class BranchDAOImpl extends GenericDAOImpl<Branch, Integer> implements BranchDAO {
    public BranchDAOImpl() {
        super(Branch.class);
    }
}