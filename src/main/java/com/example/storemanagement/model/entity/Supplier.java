package com.example.storemanagement.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SUPPLIERS")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierID;

    private String supplierName;
    private String address;
    private String phone;

    // 1. Constructor không đối số (Bắt buộc cho JPA)
    public Supplier() {
    }

    // 2. Constructor có đối số (Dùng để khởi tạo nhanh)
    public Supplier(String supplierName, String address, String phone) {
        this.supplierName = supplierName;
        this.address = address;
        this.phone = phone;
    }

    // 3. Getters & Setters
    public int getSupplierID() { return supplierID; }
    public void setSupplierID(int supplierID) { this.supplierID = supplierID; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // 4. toString (Hỗ trợ debug/in dữ liệu)
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
