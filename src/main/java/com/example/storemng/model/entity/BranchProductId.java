package com.example.storemng.model.entity;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class BranchProductId implements Serializable {

    private Integer branchID;
    private Integer productID;

    // Constructors
    public BranchProductId() {}

    public BranchProductId(Integer branchID, Integer productID) {
        this.branchID = branchID;
        this.productID = productID;
    }

    // Getter và Setter
    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    // Equals & HashCode (Bắt buộc cho Composite Key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchProductId that = (BranchProductId) o;
        return Objects.equals(branchID, that.branchID) &&
                Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchID, productID);
    }

    // ToString
    @Override
    public String toString() {
        return "BranchProductId{" +
                "branchID=" + branchID +
                ", productID=" + productID +
                '}';
    }
}
