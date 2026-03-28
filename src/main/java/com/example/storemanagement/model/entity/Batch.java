package com.example.storemanagement.model.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BATCHES")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int batchID;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "BranchID")
    private Branch branch;

    @Temporal(TemporalType.DATE)
    private Date importDate;

    private int quantity;

    // ===== Getter Setter =====
    public int getBatchID() { return batchID; }
    public void setBatchID(int batchID) { this.batchID = batchID; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }

    public Date getImportDate() { return importDate; }
    public void setImportDate(Date importDate) { this.importDate = importDate; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}