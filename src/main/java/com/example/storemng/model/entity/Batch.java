package com.example.storemng.model.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "BATCH")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchID;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;

    private LocalDate importDate;

    private Integer quantity;

    public Batch() {}

    public Long getBatchID() { return batchID; }
    public Product getProduct() { return product; }
    public Supplier getSupplier() { return supplier; }
    public LocalDate getImportDate() { return importDate; }
    public Integer getQuantity() { return quantity; }

    public void setBatchID(Long batchID) { this.batchID = batchID; }
    public void setProduct(Product product) { this.product = product; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
    public void setImportDate(LocalDate importDate) { this.importDate = importDate; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Batch " + batchID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Batch that)) return false;
        return batchID != null && batchID.equals(that.batchID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
