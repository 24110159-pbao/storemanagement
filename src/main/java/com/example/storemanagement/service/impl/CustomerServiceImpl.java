package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.CustomerDAO;
import com.example.storemanagement.dao.impl.CustomerDAOImpl;
import com.example.storemanagement.model.entity.Customer;
import com.example.storemanagement.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO dao = new CustomerDAOImpl();

    @Override
    public void add(Customer c) {
        dao.save(c);
    }

    @Override
    public void update(Customer c) {
        dao.update(c);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public Customer getById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<Customer> getAll() {
        return dao.findAll();
    }
}