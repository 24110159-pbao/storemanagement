package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Branch;
import com.example.storemanagement.model.entity.Employee;
import com.example.storemanagement.service.EmployeeService;
import com.example.storemanagement.service.impl.EmployeeServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeController {

    private EmployeeService service = new EmployeeServiceImpl();

    public List<Employee> getAllEmployees() {
        return service.getAll();
    }

    public void addEmployee(String name, String position, double salary, int branchId) {
        Employee e = new Employee();
        e.setEmployeeName(name);
        e.setPosition(position);
        e.setSalary(BigDecimal.valueOf(salary));

        // set branch bằng ID ( entity đã map)
        Branch b = new Branch();
        b.setBranchID(branchId);
        e.setBranch(b);

        service.add(e);
    }

    public void updateEmployee(int id, String name, String position, double salary, int branchId) {
        Employee e = service.getById(id);
        if (e != null) {
            e.setEmployeeName(name);
            e.setPosition(position);
            e.setSalary(BigDecimal.valueOf(salary));

            Branch b = new Branch();
            b.setBranchID(branchId);
            e.setBranch(b);

            service.update(e);
        }
    }

    public void deleteEmployee(int id) {
        service.delete(id);
    }
}