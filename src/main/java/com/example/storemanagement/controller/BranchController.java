package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Branch;
import com.example.storemanagement.service.BranchService;
import com.example.storemanagement.service.impl.BranchServiceImpl;

import java.util.List;

public class BranchController {

    // service xử lý logic Branch
    private BranchService service = new BranchServiceImpl();

    // lấy tất cả chi nhánh
    // dùng: branchController.getAll();
    public List<Branch> getAll() {
        return service.getAll();
    }

    // thêm chi nhánh mới
    // dùng: branchController.addBranch("CN1", "HCM", "0123456789");
    public void addBranch(String name, String address, String phone) {
        Branch b = new Branch();
        b.setBranchName(name);
        b.setAddress(address);
        b.setPhone(phone);
        service.add(b);
    }

    // cập nhật chi nhánh theo id
    // dùng: branchController.updateBranch(1, "CN mới", "HN", "0987654321");
    public void updateBranch(int id, String name, String address, String phone) {
        Branch b = service.getById(id);
        if (b != null) {
            b.setBranchName(name);
            b.setAddress(address);
            b.setPhone(phone);
            service.update(b);
        }
    }

    // xóa chi nhánh theo id
    // dùng: branchController.deleteBranch(1);
    public void deleteBranch(int id) {
        service.delete(id);
    }
}