package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Customer;
import java.util.List;

public interface CustomerService {
    void add(Customer c);
    void update(Customer c);
    void delete(int id);
    Customer getById(int id);
    List<Customer> getAll();
}