package com.example.storemng.model.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Batch> batches = new ArrayList<>();

    public Product() {}

    public Product(String productName, BigDecimal unitPrice) {
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    public Long getProductID() { return productID; }
    public String getProductName() { return productName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Boolean getStatus() { return status; }
    public Category getCategory() { return category; }
    public List<Batch> getBatches() { return batches; }

    public void setProductID(Long productID) { this.productID = productID; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public void setStatus(Boolean status) { this.status = status; }
    public void setCategory(Category category) { this.category = category; }
    public void setBatches(List<Batch> batches) { this.batches = batches; }

    @Override
    public String toString() {
        return productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product that)) return false;
        return productID != null && productID.equals(that.productID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}