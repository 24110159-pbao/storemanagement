package com.example.storemanagement.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BRANCHES")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BranchID")
    private Integer branchID;

    @Column(name = "BranchName", nullable = false, length = 100)
    private String branchName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Phone")
    private String phone;

    public Branch() {}

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

    // QUAN TRỌNG cho ComboBox
    @Override
    public String toString() {
        return branchName;
    }
}