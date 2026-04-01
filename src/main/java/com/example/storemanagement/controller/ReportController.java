package com.example.storemanagement.controller;

import com.example.storemanagement.model.dto.CustomerInvoiceDetailDTO;
import com.example.storemanagement.model.dto.ProfitReportDTO;
import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;
import com.example.storemanagement.service.ReportService;
import com.example.storemanagement.service.impl.ReportServiceImpl;

import java.util.List;

public class ReportController {

    // service xử lý logic báo cáo
    private final ReportService reportService = new ReportServiceImpl();

    // lấy danh sách các năm có dữ liệu
    // dùng: reportController.getAvailableYears();
    public List<Integer> getAvailableYears() {
        return reportService.getAvailableYears();
    }

    // lấy thống kê theo năm (doanh thu, số hóa đơn,...)
    // dùng: reportController.getYearStatistics(2024);
    public YearStatisticsDTO getYearStatistics(int year) {
        return reportService.getYearStatistics(year);
    }

    // lấy báo cáo lợi nhuận theo năm
    // dùng: reportController.getProfitReportByYear(2024);
    public ProfitReportDTO getProfitReportByYear(int year) {
        return reportService.getProfitReportByYear(year);
    }

    // lấy top khách hàng theo tháng
    // dùng: reportController.getTopCustomersByMonth(2024, 5);
    public List<TopCustomerDTO> getTopCustomersByMonth(int year, int month) {
        return reportService.getTopCustomersByMonth(year, month);
    }

    // lấy chi tiết hóa đơn của 1 khách hàng theo tháng
    // dùng: reportController.getCustomerInvoiceDetailsByMonth(2024, 5, 1);
    public List<CustomerInvoiceDetailDTO> getCustomerInvoiceDetailsByMonth(int year, int month, int customerId) {
        return reportService.getCustomerInvoiceDetailsByMonth(year, month, customerId);
    }
}