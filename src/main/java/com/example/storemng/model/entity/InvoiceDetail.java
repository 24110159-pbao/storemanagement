package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "INVOICE_DETAILS")
public class InvoiceDetail {

    @EmbeddedId
    private InvoiceDetailId id;

    @ManyToOne
    @MapsId("invoiceID")
    @JoinColumn(name = "InvoiceID")
    private Invoice invoice;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    // Getter và Setter
    public InvoiceDetailId getId() {
        return id;
    }

    public void setId(InvoiceDetailId id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    // ToString
    @Override
    public String toString() {
        return "InvoiceDetail{" +
                "id=" + id +
                ", product=" + (product != null ? product.getProductName() : "null") +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
