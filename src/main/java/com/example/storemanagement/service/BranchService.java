package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Branch;
import java.util.List;

public interface BranchService {
    List<Branch> getAll();
    void add(Branch branch);
    void update(Branch branch);
    void delete(int id);
    Branch getById(int id);
}