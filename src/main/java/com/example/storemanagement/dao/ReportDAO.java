package com.example.storemanagement.dao;

import com.example.storemanagement.model.dto.CustomerInvoiceDetailDTO;
import com.example.storemanagement.model.dto.ProfitReportDTO;
import com.example.storemanagement.model.dto.RevenueDTO;
import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;

import java.util.List;

public interface ReportDAO {

    // lấy danh sách các năm có dữ liệu
    // dùng: List<Integer> years = reportDAO.findAvailableYears();
    List<Integer> findAvailableYears();

    // lấy thống kê theo năm
    // dùng: YearStatisticsDTO stats = reportDAO.findYearStatistics(2024);
    YearStatisticsDTO findYearStatistics(int year);

    // lấy doanh thu theo từng tháng trong năm
    // dùng: List<RevenueDTO> revenueList = reportDAO.findRevenueByMonth(2024);
    List<RevenueDTO> findRevenueByMonth(int year);

    // lấy báo cáo lợi nhuận theo năm
    // dùng: ProfitReportDTO profit = reportDAO.findProfitReportByYear(2024);
    ProfitReportDTO findProfitReportByYear(int year);

    // lấy top khách hàng theo tháng
    // dùng: List<TopCustomerDTO> topCustomers = reportDAO.findTopCustomersByMonth(2024, 5);
    List<TopCustomerDTO> findTopCustomersByMonth(int year, int month);

    // lấy chi tiết hóa đơn của 1 khách hàng theo tháng
    // dùng: List<CustomerInvoiceDetailDTO> details = reportDAO.findCustomerInvoiceDetailsByMonth(2024, 5, 1);
    List<CustomerInvoiceDetailDTO> findCustomerInvoiceDetailsByMonth(int year, int month, int customerId);
}