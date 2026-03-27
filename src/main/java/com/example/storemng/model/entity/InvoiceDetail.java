package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "INVOICE_DETAIL")
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

    public InvoiceDetail() {}

    public InvoiceDetailId getId() { return id; }
    public Invoice getInvoice() { return invoice; }
    public Product getProduct() { return product; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    public void setId(InvoiceDetailId id) { this.id = id; }
    public void setInvoice(Invoice invoice) { this.invoice = invoice; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public String toString() {
        return "InvoiceDetail";
    }
}
