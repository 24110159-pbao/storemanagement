package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeID;

    @Column(nullable = false)
    private String employeeName;

    private String position;

    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "BranchID")
    private Branch branch;

    @OneToMany(mappedBy = "employee")
    private List<Invoice> invoices;

    // Getter và Setter
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

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    // ToString
    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", branch=" + (branch != null ? branch.getBranchName() : "null") +
                '}';
    }
}
