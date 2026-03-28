package com.example.storemanagement.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private int customerID;

    @Column(name = "CustomerName", nullable = false)
    private String customerName;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;

    // ===== CONSTRUCTOR =====
    public Customer() {}

    public Customer(String customerName, String phone, String address) {
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
    }

    // ===== GETTER & SETTER =====
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // ===== toString =====
    @Override
    public String toString() {
        return customerName;
    }
}