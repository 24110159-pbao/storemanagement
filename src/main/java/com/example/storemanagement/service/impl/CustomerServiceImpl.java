package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.CustomerDAO;
import com.example.storemanagement.dao.impl.CustomerDAOImpl;
import com.example.storemanagement.model.entity.Customer;
import com.example.storemanagement.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO dao = new CustomerDAOImpl();

    @Override
    public List<Customer> getAll() {
        return dao.findAll();
    }

    @Override
    public void save(Customer c) {
        dao.save(c);
    }
}
