package com.example.storemanagement.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private Integer employeeID;

    @Column(name = "EmployeeName", nullable = false, length = 100)
    private String employeeName;

    @Column(name = "Position", length = 50)
    private String position;

    @Column(name = "Salary")
    private BigDecimal salary;

    // ===== RELATIONSHIP =====
    @ManyToOne
    @JoinColumn(name = "BranchID")
    private Branch branch;

    // ===== CONSTRUCTOR =====
    public Employee() {}

    // ===== GETTER / SETTER =====
    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }


    @Override
    public String toString() {
        return employeeName;
    }
}