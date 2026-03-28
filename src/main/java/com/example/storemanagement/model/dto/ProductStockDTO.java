package com.example.storemanagement.model.dto;

import java.math.BigDecimal;

public class ProductStockDTO {
    private int productId;
    private String productName;
    private BigDecimal  unitPrice;
    private long totalQuantity;

    public ProductStockDTO(int productId, String productName, BigDecimal unitPrice, long totalQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.totalQuantity = totalQuantity;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal  getUnitPrice() { return unitPrice; }
    public long getTotalQuantity() { return totalQuantity; }
}