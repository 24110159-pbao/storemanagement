package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Branch;
import com.example.storemanagement.service.BranchService;
import com.example.storemanagement.service.impl.BranchServiceImpl;

import java.util.List;

public class BranchController {

    private BranchService service = new BranchServiceImpl();

    public List<Branch> getAll() {
        return service.getAll();
    }

    public void addBranch(String name, String address, String phone) {
        Branch b = new Branch();
        b.setBranchName(name);
        b.setAddress(address);
        b.setPhone(phone);
        service.add(b);
    }

    public void updateBranch(int id, String name, String address, String phone) {
        Branch b = service.getById(id);
        if (b != null) {
            b.setBranchName(name);
            b.setAddress(address);
            b.setPhone(phone);
            service.update(b);
        }
    }

    public void deleteBranch(int id) {
        service.delete(id);
    }
}