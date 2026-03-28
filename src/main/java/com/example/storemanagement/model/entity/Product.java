package com.example.storemanagement.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;

    @Column(name = "ProductName", nullable = false)
    private String productName;


    @Column(name = "UnitPrice", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "Status")
    private boolean status;

    // ===== MANY TO ONE -> CATEGORY =====
    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    // ===== ONE TO MANY -> BATCH =====
    @OneToMany(mappedBy = "product")
    private List<Batch> batches;

    // ===== ONE TO MANY -> INVOICE DETAIL =====
    @OneToMany(mappedBy = "product")
    private List<InvoiceDetail> invoiceDetails;

    // ===== MANY TO MANY qua BranchProduct =====
    @OneToMany(mappedBy = "product")
    private List<BranchProduct> branchProducts;

    // ===== CONSTRUCTOR =====
    public Product() {
    }

    public Product(String productName, BigDecimal  unitPrice, boolean status) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    // ===== GETTER SETTER =====

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal  getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal  unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public List<BranchProduct> getBranchProducts() {
        return branchProducts;
    }

    public void setBranchProducts(List<BranchProduct> branchProducts) {
        this.branchProducts = branchProducts;
    }
}