package com.example.storemanagement.model.dto;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * DTO doanh thu theo tháng.
 * Dùng cho chart hoặc báo cáo.
 */
public class RevenueDTO {

    private final int month;           // Tháng (1-12)
    private final BigDecimal revenue;  // Doanh thu
    private final long invoiceCount;   // Số hóa đơn

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

    // Trả về tên tháng ngắn theo tiếng Việt (Thg 1, Thg 2,...)
    public String getMonthLabel() {
        return Month.of(month)
                .getDisplayName(TextStyle.SHORT, new Locale("vi", "VN"));
    }
}