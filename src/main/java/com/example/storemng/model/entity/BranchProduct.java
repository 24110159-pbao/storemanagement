package com.example.storemng.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "BRANCH_PRODUCT")
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

    public BranchProduct() {}

    public BranchProductId getId() { return id; }
    public Branch getBranch() { return branch; }
    public Product getProduct() { return product; }
    public Integer getQuantity() { return quantity; }

    public void setId(BranchProductId id) { this.id = id; }
    public void setBranch(Branch branch) { this.branch = branch; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "BranchProduct";
    }
}
