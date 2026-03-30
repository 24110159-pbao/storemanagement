package com.example.storemanagement.dao;

import com.example.storemanagement.model.dto.CustomerInvoiceDetailDTO;
import com.example.storemanagement.model.dto.RevenueDTO;
import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;

import java.util.List;

public interface ReportDAO {
    List<Integer> findAvailableYears();
    YearStatisticsDTO findYearStatistics(int year);
    List<RevenueDTO> findRevenueByMonth(int year);
    List<TopCustomerDTO> findTopCustomersByMonth(int year, int month);
    List<CustomerInvoiceDetailDTO> findCustomerInvoiceDetailsByMonth(int year, int month, int customerId);
}
