package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Customer;
import java.util.List;

public interface CustomerDAO {

    // lưu khách hàng mới vào DB
    // dùng: customerDAO.save(customer);
    void save(Customer customer);

    // cập nhật khách hàng đã tồn tại
    // dùng: customerDAO.update(customer);
    void update(Customer customer);

    // xóa khách hàng theo id
    // dùng: customerDAO.delete(1);
    void delete(int id);

    // tìm khách hàng theo id
    // dùng: Customer c = customerDAO.findById(1);
    Customer findById(int id);

    // lấy tất cả khách hàng
    // dùng: List<Customer> list = customerDAO.findAll();
    List<Customer> findAll();
}