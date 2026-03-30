package com.example.storemanagement.model.id;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class InvoiceDetailId implements Serializable {

    private Integer invoiceID;
    private Integer productID;

    // Constructors
    public InvoiceDetailId() {}

    public InvoiceDetailId(Integer invoiceID, Integer productID) {
        this.invoiceID = invoiceID;
        this.productID = productID;
    }

    // Getter và Setter
    public Integer getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Integer invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    // Equals & HashCode (Bắt buộc)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDetailId that = (InvoiceDetailId) o;
        return Objects.equals(invoiceID, that.invoiceID) &&
                Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceID, productID);
    }


}
