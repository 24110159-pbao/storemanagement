package com.example.storemanagement.model.entity;


import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "BATCHES")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer batchID;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;

    private LocalDate importDate;

    private Integer quantity;

    // Getter và Setter
    public Integer getBatchID() {
        return batchID;
    }

    public void setBatchID(Integer batchID) {
        this.batchID = batchID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // ToString
    @Override
    public String toString() {
        return "Batch{" +
                "batchID=" + batchID +
                ", product=" + (product != null ? product.getProductName() : "null") +
                ", supplier=" + (supplier != null ? supplier.getSupplierName() : "null") +
                ", importDate=" + importDate +
                ", quantity=" + quantity +
                '}';
    }
}
