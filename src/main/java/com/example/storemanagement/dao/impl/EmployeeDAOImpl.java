package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.EmployeeDAO;
import com.example.storemanagement.model.entity.Employee;

public class EmployeeDAOImpl extends GenericDAOImpl<Employee, Integer> implements EmployeeDAO {
    public EmployeeDAOImpl() {
        super(Employee.class);
    }
}