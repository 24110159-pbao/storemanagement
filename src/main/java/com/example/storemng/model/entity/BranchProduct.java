package com.example.storemng.model.entity;


import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "BRANCH_PRODUCTS")
public class BranchProduct {

    @EmbeddedId
    private BranchProductId id;

    @ManyToOne
    @MapsId("branchID")
    @JoinColumn(name = "BranchID")
    private Branch branch;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "ProductID")
    private Product product;

    private Integer quantity;

    // Getter và Setter
    public BranchProductId getId() {
        return id;
    }

    public void setId(BranchProductId id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        return "BranchProduct{" +
                "id=" + id +
                ", branch=" + (branch != null ? branch.getBranchName() : "null") +
                ", product=" + (product != null ? product.getProductName() : "null") +
                ", quantity=" + quantity +
                '}';
    }
}
