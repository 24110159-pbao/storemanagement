package com.example.storemanagement.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BranchProductId implements Serializable {

    @Column(name = "BranchID")
    private int branchID;

    @Column(name = "ProductID")
    private int productID;

    public BranchProductId() {}

    public BranchProductId(int branchID, int productID) {
        this.branchID = branchID;
        this.productID = productID;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BranchProductId)) return false;
        BranchProductId that = (BranchProductId) o;
        return branchID == that.branchID &&
                productID == that.productID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchID, productID);
    }
}