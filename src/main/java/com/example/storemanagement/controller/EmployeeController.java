package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Branch;
import com.example.storemanagement.model.entity.Employee;
import com.example.storemanagement.service.EmployeeService;
import com.example.storemanagement.service.impl.EmployeeServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeController {

    // service xử lý logic Employee
    private EmployeeService service = new EmployeeServiceImpl();

    // lấy tất cả nhân viên
    // dùng: employeeController.getAllEmployees();
    public List<Employee> getAllEmployees() {
        return service.getAll();
    }

    // thêm nhân viên mới
    // dùng: employeeController.addEmployee("An", "Manager", 10000, 1);
    public void addEmployee(String name, String position, double salary, int branchId) {
        Employee e = new Employee();
        e.setEmployeeName(name);
        e.setPosition(position);
        e.setSalary(BigDecimal.valueOf(salary));

        // tạo branch theo id để gán cho employee
        Branch b = new Branch();
        b.setBranchID(branchId);
        e.setBranch(b);

        service.add(e);
    }

    // cập nhật nhân viên theo id
    // dùng: employeeController.updateEmployee(1, "Bình", "Staff", 8000, 2);
    public void updateEmployee(int id, String name, String position, double salary, int branchId) {
        Employee e = service.getById(id);
        if (e != null) {
            e.setEmployeeName(name);
            e.setPosition(position);
            e.setSalary(BigDecimal.valueOf(salary));

            // cập nhật lại branch
            Branch b = new Branch();
            b.setBranchID(branchId);
            e.setBranch(b);

            service.update(e);
        }
    }

    // xóa nhân viên theo id
    // dùng: employeeController.deleteEmployee(1);
    public void deleteEmployee(int id) {
        service.delete(id);
    }
}