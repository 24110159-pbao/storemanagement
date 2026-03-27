package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "SUPPLIERS")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierID;

    @Column(nullable = false, length = 100)
    private String supplierName;

    private String address;

    private String phone;

    public Supplier() {
    }

    // Getters
    public Integer getSupplierID() { return supplierID; }
    public String getSupplierName() { return supplierName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }

    // Setters
    public void setSupplierID(Integer supplierID) { this.supplierID = supplierID; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierID=" + supplierID +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
