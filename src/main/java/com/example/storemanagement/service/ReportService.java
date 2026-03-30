package com.example.storemanagement.service;

import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;

import java.util.List;

public interface ReportService {
    List<Integer> getAvailableYears();
    YearStatisticsDTO getYearStatistics(int year);
    List<TopCustomerDTO> getTopCustomersByMonth(int year, int month);
}
