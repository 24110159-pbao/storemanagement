package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Customer;
import com.example.storemanagement.service.CustomerService;
import com.example.storemanagement.service.impl.CustomerServiceImpl;

import java.util.List;

public class CustomerController {

    private final CustomerService service = new CustomerServiceImpl();

    public void addCustomer(String name, String phone, String address) {
        Customer c = new Customer();
        c.setCustomerName(name);
        c.setPhone(phone);
        c.setAddress(address);

        service.add(c);
    }

    public void updateCustomer(int id, String name, String phone, String address) {
        Customer c = service.getById(id);
        if (c != null) {
            c.setCustomerName(name);
            c.setPhone(phone);
            c.setAddress(address);
            service.update(c);
        }
    }

    public void deleteCustomer(int id) {
        service.delete(id);
    }

    public List<Customer> getAllCustomers() {
        return service.getAll();
    }
}