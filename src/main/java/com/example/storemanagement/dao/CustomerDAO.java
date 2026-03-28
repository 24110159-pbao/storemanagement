package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Customer;
import java.util.List;

public interface CustomerDAO {
    void save(Customer customer);
    void update(Customer customer);
    void delete(int id);
    Customer findById(int id);
    List<Customer> findAll();
}