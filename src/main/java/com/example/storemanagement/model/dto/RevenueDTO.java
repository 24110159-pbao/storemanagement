package com.example.storemanagement.model.dto;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class RevenueDTO {

    private final int month;
    private final BigDecimal revenue;
    private final long invoiceCount;

    public RevenueDTO(int month, BigDecimal revenue, long invoiceCount) {
        this.month = month;
        this.revenue = revenue != null ? revenue : BigDecimal.ZERO;
        this.invoiceCount = invoiceCount;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public long getInvoiceCount() {
        return invoiceCount;
    }

    public String getMonthLabel() {
        return Month.of(month).getDisplayName(TextStyle.SHORT, new Locale("vi", "VN"));
    }
}
