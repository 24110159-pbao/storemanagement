package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.util.*;


@Entity
@Table(name = "BRANCHES")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer branchID;

    @Column(nullable = false)
    private String branchName;

    private String address;

    private String phone;

    @OneToMany(mappedBy = "branch")
    private List<Employee> employees;

    @OneToMany(mappedBy = "branch")
    private List<BranchProduct> branchProducts;

    @OneToMany(mappedBy = "branch")
    private List<Invoice> invoices;

    // Getter và Setter
    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<BranchProduct> getBranchProducts() {
        return branchProducts;
    }

    public void setBranchProducts(List<BranchProduct> branchProducts) {
        this.branchProducts = branchProducts;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    // ToString (Loại bỏ các List để đảm bảo hiệu năng và tránh lỗi đệ quy)
    @Override
    public String toString() {
        return "Branch{" +
                "branchID=" + branchID +
                ", branchName='" + branchName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
