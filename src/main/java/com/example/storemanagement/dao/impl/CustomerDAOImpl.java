package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.CustomerDAO;
import com.example.storemanagement.model.entity.Customer;

public class CustomerDAOImpl extends GenericDAOImpl<Customer, Integer> implements CustomerDAO {
    public CustomerDAOImpl() {
        super(Customer.class);
    }
}