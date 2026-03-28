package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();
    void add(Employee employee);
    void update(Employee employee);
    void delete(int id);
    Employee getById(int id);
}