package com.example.storemng.model.entity;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BranchProductId implements Serializable {

    private Long branchID;
    private Long productID;

    public BranchProductId() {}

    public Long getBranchID() { return branchID; }
    public Long getProductID() { return productID; }

    public void setBranchID(Long branchID) { this.branchID = branchID; }
    public void setProductID(Long productID) { this.productID = productID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BranchProductId that)) return false;
        return Objects.equals(branchID, that.branchID) &&
                Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchID, productID);
    }
}