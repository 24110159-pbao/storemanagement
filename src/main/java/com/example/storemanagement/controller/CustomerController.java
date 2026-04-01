package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Customer;
import com.example.storemanagement.service.CustomerService;
import com.example.storemanagement.service.impl.CustomerServiceImpl;

import java.util.List;

public class CustomerController {

    // service xử lý logic Customer
    private final CustomerService service = new CustomerServiceImpl();

    // thêm khách hàng mới
    // dùng: customerController.addCustomer("An", "0123456789", "HCM");
    public void addCustomer(String name, String phone, String address) {
        Customer c = new Customer();
        c.setCustomerName(name);
        c.setPhone(phone);
        c.setAddress(address);

        service.add(c);
    }

    // cập nhật khách hàng theo id
    // dùng: customerController.updateCustomer(1, "Bình", "0987654321", "HN");
    public void updateCustomer(int id, String name, String phone, String address) {
        Customer c = service.getById(id);
        if (c != null) {
            c.setCustomerName(name);
            c.setPhone(phone);
            c.setAddress(address);
            service.update(c);
        }
    }

    // xóa khách hàng theo id
    // dùng: customerController.deleteCustomer(1);
    public void deleteCustomer(int id) {
        service.delete(id);
    }

    // lấy tất cả khách hàng
    // dùng: customerController.getAllCustomers();
    public List<Customer> getAllCustomers() {
        return service.getAll();
    }
}