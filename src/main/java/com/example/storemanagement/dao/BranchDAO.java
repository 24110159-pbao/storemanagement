package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Branch;
import java.util.List;

public interface BranchDAO {
    List<Branch> findAll();
    Branch findById(int id);
    void save(Branch branch);
    void update(Branch branch);
    void delete(int id);
}