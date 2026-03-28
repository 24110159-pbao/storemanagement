package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.EmployeeDAO;
import com.example.storemanagement.dao.impl.EmployeeDAOImpl;
import com.example.storemanagement.model.entity.Employee;
import com.example.storemanagement.service.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO dao = new EmployeeDAOImpl();

    @Override
    public List<Employee> getAll() {
        return dao.findAll();
    }

    @Override
    public void add(Employee employee) {
        dao.save(employee);
    }

    @Override
    public void update(Employee employee) {
        dao.update(employee);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public Employee getById(int id) {
        return dao.findById(id);
    }
}