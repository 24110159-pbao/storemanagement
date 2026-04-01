package com.example.storemanagement.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO thông tin khách hàng top mua nhiều nhất.
 * Dùng cho dashboard hoặc báo cáo CRM.
 */
public class TopCustomerDTO {

    private final Integer customerId;       // ID khách
    private final String customerName;      // Tên khách
    private final String phone;             // SĐT
    private final long invoiceCount;        // Số hóa đơn
    private final BigDecimal totalSpent;    // Tổng chi tiêu
    private final LocalDate lastPurchaseDate; // Ngày mua gần nhất

    public TopCustomerDTO(Integer customerId,
                          String customerName,
                          String phone,
                          long invoiceCount,
                          BigDecimal totalSpent,
                          LocalDate lastPurchaseDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone = phone;
        this.invoiceCount = invoiceCount;
        this.totalSpent = totalSpent != null ? totalSpent : BigDecimal.ZERO;
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public long getInvoiceCount() {
        return invoiceCount;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }
}