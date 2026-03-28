package com.example.storemanagement.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUPPLIERS")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID")
    private Integer supplierId;

    @Column(name = "SupplierName", nullable = false, length = 100)
    private String supplierName;

    @Column(name = "Address", length = 255)
    private String address;

    @Column(name = "Phone", length = 20)
    private String phone;

    // =========================
    // RELATIONSHIPS
    // =========================

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}