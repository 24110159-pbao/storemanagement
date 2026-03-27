package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;

    @Column(nullable = false)
    private String employeeName;

    private String position;

    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "BranchID")
    private Branch branch;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Invoice> invoices = new ArrayList<>();

    public Employee() {}

    public Long getEmployeeID() { return employeeID; }
    public String getEmployeeName() { return employeeName; }
    public String getPosition() { return position; }
    public BigDecimal getSalary() { return salary; }
    public Branch getBranch() { return branch; }
    public List<Invoice> getInvoices() { return invoices; }

    public void setEmployeeID(Long employeeID) { this.employeeID = employeeID; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public void setBranch(Branch branch) { this.branch = branch; }
    public void setInvoices(List<Invoice> invoices) { this.invoices = invoices; }

    @Override
    public String toString() {
        return employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee that)) return false;
        return employeeID != null && employeeID.equals(that.employeeID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}