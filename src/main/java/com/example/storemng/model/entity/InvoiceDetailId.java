package com.example.storemng.model.entity;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvoiceDetailId implements Serializable {

    private Long invoiceID;
    private Long productID;

    public InvoiceDetailId() {}

    public Long getInvoiceID() { return invoiceID; }
    public Long getProductID() { return productID; }

    public void setInvoiceID(Long invoiceID) { this.invoiceID = invoiceID; }
    public void setProductID(Long productID) { this.productID = productID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceDetailId that)) return false;
        return Objects.equals(invoiceID, that.invoiceID) &&
                Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceID, productID);
    }
}