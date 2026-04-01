package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Employee;
import java.util.List;

public interface EmployeeDAO {

    // lấy tất cả nhân viên
    // dùng: List<Employee> list = employeeDAO.findAll();
    List<Employee> findAll();

    // lưu nhân viên mới vào DB
    // dùng: employeeDAO.save(employee);
    void save(Employee employee);

    // cập nhật nhân viên đã tồn tại
    // dùng: employeeDAO.update(employee);
    void update(Employee employee);

    // xóa nhân viên theo id
    // dùng: employeeDAO.delete(1);
    void delete(int id);

    // tìm nhân viên theo id
    // dùng: Employee e = employeeDAO.findById(1);
    Employee findById(int id);
}