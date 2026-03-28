package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();
    void save(Customer c);
}