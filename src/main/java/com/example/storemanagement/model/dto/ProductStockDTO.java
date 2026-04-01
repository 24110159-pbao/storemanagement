package com.example.storemanagement.model.dto;

import java.math.BigDecimal;

/**
 * DTO thông tin tồn kho sản phẩm.
 * Dùng cho báo cáo kho hoặc dashboard.
 */
public class ProductStockDTO {

    private int productId;        // ID sản phẩm
    private String productName;   // Tên sản phẩm
    private BigDecimal unitPrice; // Giá bán
    private long totalQuantity;   // Số lượng tồn

    public ProductStockDTO(int productId, String productName, BigDecimal unitPrice, long totalQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice != null ? unitPrice : BigDecimal.ZERO; // tránh null
        this.totalQuantity = totalQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }
}