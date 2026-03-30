package com.example.storemanagement.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TopCustomerDTO {

    private final Integer customerId;
    private final String customerName;
    private final String phone;
    private final long invoiceCount;
    private final BigDecimal totalSpent;
    private final LocalDate lastPurchaseDate;

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
