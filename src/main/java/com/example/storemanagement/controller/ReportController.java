package com.example.storemanagement.controller;

import com.example.storemanagement.model.dto.CustomerInvoiceDetailDTO;
import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;
import com.example.storemanagement.service.ReportService;
import com.example.storemanagement.service.impl.ReportServiceImpl;

import java.util.List;

public class ReportController {

    private final ReportService reportService = new ReportServiceImpl();

    public List<Integer> getAvailableYears() {
        return reportService.getAvailableYears();
    }

    public YearStatisticsDTO getYearStatistics(int year) {
        return reportService.getYearStatistics(year);
    }

    public List<TopCustomerDTO> getTopCustomersByMonth(int year, int month) {
        return reportService.getTopCustomersByMonth(year, month);
    }

    public List<CustomerInvoiceDetailDTO> getCustomerInvoiceDetailsByMonth(int year, int month, int customerId) {
        return reportService.getCustomerInvoiceDetailsByMonth(year, month, customerId);
    }
}
