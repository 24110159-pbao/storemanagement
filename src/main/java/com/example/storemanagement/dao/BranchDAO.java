package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Branch;
import java.util.List;

public interface BranchDAO {

    // lấy tất cả chi nhánh
    // dùng: List<Branch> list = branchDAO.findAll();
    List<Branch> findAll();

    // tìm chi nhánh theo id
    // dùng: Branch b = branchDAO.findById(1);
    Branch findById(int id);

    // lưu chi nhánh mới vào DB
    // dùng: branchDAO.save(branch);
    void save(Branch branch);

    // cập nhật chi nhánh đã tồn tại
    // dùng: branchDAO.update(branch);
    void update(Branch branch);

    // xóa chi nhánh theo id
    // dùng: branchDAO.delete(1);
    void delete(int id);
}