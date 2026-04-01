package com.example.storemanagement.model.dto;

/**
 * DTO dòng sản phẩm trong hóa đơn.
 * Dùng trong InvoiceInputDTO khi client gửi dữ liệu lên server.
 */
public class InvoiceItemDTO {

    private int productId;   // ID sản phẩm
    private int quantity;    // Số lượng mua
    private double unitPrice; // Giá bán tại thời điểm tạo hóa đơn

    // Constructor rỗng (cho JSON mapping)
    public InvoiceItemDTO() {}

    public InvoiceItemDTO(int productId, int quantity, double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}