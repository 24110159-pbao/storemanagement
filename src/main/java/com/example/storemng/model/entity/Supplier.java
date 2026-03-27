package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierID;

    @Column(nullable = false, length = 100)
    private String supplierName;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String phone;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Batch> batches = new ArrayList<>();

    public Supplier() {}

    public Supplier(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getSupplierID() { return supplierID; }
    public String getSupplierName() { return supplierName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public List<Batch> getBatches() { return batches; }

    public void setSupplierID(Long supplierID) { this.supplierID = supplierID; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBatches(List<Batch> batches) { this.batches = batches; }

    @Override
    public String toString() {
        return supplierName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier that)) return false;
        return supplierID != null && supplierID.equals(that.supplierID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}