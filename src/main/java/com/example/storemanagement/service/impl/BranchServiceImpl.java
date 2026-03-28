package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.BranchDAO;
import com.example.storemanagement.dao.impl.BranchDAOImpl;
import com.example.storemanagement.model.entity.Branch;
import com.example.storemanagement.service.BranchService;

import java.util.List;

public class BranchServiceImpl implements BranchService {

    private BranchDAO dao = new BranchDAOImpl();

    @Override
    public List<Branch> getAll() {
        return dao.findAll();
    }

    @Override
    public void add(Branch branch) {
        dao.save(branch);
    }

    @Override
    public void update(Branch branch) {
        dao.update(branch);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public Branch getById(int id) {
        return dao.findById(id);
    }
}