package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.ReportDAO;
import com.example.storemanagement.dao.impl.ReportDAOImpl;
import com.example.storemanagement.model.dto.RevenueDTO;
import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;
import com.example.storemanagement.service.ReportService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {

    private final ReportDAO reportDAO = new ReportDAOImpl();

    @Override
    public List<Integer> getAvailableYears() {
        List<Integer> years = reportDAO.findAvailableYears();
        if (years.isEmpty()) {
            return List.of(LocalDate.now().getYear());
        }
        return years;
    }

    @Override
    public YearStatisticsDTO getYearStatistics(int year) {
        YearStatisticsDTO dto = reportDAO.findYearStatistics(year);
        List<RevenueDTO> normalizedRevenue = normalizeMonthlyRevenue(reportDAO.findRevenueByMonth(year));

        dto.setMonthlyRevenues(normalizedRevenue);
        RevenueDTO bestMonth = normalizedRevenue.stream()
                .max(Comparator.comparing(RevenueDTO::getRevenue))
                .orElse(new RevenueDTO(1, BigDecimal.ZERO, 0));

        dto.setBestMonth(bestMonth.getMonth());
        dto.setBestMonthRevenue(bestMonth.getRevenue());
        return dto;
    }

    @Override
    public List<TopCustomerDTO> getTopCustomersByMonth(int year, int month) {
        return reportDAO.findTopCustomersByMonth(year, month);
    }

    private List<RevenueDTO> normalizeMonthlyRevenue(List<RevenueDTO> rawRevenue) {
        Map<Integer, RevenueDTO> revenueByMonth = rawRevenue.stream()
                .collect(Collectors.toMap(RevenueDTO::getMonth, Function.identity()));

        List<RevenueDTO> normalized = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            normalized.add(revenueByMonth.getOrDefault(month, new RevenueDTO(month, BigDecimal.ZERO, 0)));
        }
        return normalized;
    }
}
