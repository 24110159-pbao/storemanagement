package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "BRANCH")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchID;

    @Column(nullable = false)
    private String branchName;

    private String address;
    private String phone;

    @OneToMany(mappedBy = "branch")
    private List<Employee> employees = new ArrayList<>();

    public Branch() {}

    public Long getBranchID() { return branchID; }
    public String getBranchName() { return branchName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public List<Employee> getEmployees() { return employees; }

    public void setBranchID(Long branchID) { this.branchID = branchID; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    @Override
    public String toString() {
        return branchName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branch that)) return false;
        return branchID != null && branchID.equals(that.branchID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
