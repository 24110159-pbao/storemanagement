package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Employee;
import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();
    void save(Employee employee);
    void update(Employee employee);
    void delete(int id);
    Employee findById(int id);
}