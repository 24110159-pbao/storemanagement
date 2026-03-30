package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.ReportDAO;
import com.example.storemanagement.model.dto.CustomerInvoiceDetailDTO;
import com.example.storemanagement.model.dto.RevenueDTO;
import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {

    @Override
    public List<Integer> findAvailableYears() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select distinct year(i.invoiceDate) from Invoice i " +
                            "where i.invoiceDate is not null " +
                            "order by year(i.invoiceDate) desc",
                    Integer.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public YearStatisticsDTO findYearStatistics(int year) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Object[] row = em.createQuery(
                    "select coalesce(sum(i.totalAmount), 0), count(i), count(distinct c.customerID) " +
                            "from Invoice i left join i.customer c " +
                            "where year(i.invoiceDate) = :year",
                    Object[].class
            ).setParameter("year", year).getSingleResult();

            YearStatisticsDTO dto = new YearStatisticsDTO(year);
            dto.setTotalRevenue(toBigDecimal(row[0]));
            dto.setTotalInvoices(toLong(row[1]));
            dto.setTotalCustomers(toLong(row[2]));
            return dto;
        } finally {
            em.close();
        }
    }

    @Override
    public List<RevenueDTO> findRevenueByMonth(int year) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select new com.example.storemanagement.model.dto.RevenueDTO(" +
                            "month(i.invoiceDate), sum(i.totalAmount), count(i)) " +
                            "from Invoice i " +
                            "where year(i.invoiceDate) = :year " +
                            "group by month(i.invoiceDate) " +
                            "order by month(i.invoiceDate)",
                    RevenueDTO.class
            ).setParameter("year", year).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<TopCustomerDTO> findTopCustomersByMonth(int year, int month) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select new com.example.storemanagement.model.dto.TopCustomerDTO(" +
                            "c.customerID, c.customerName, c.phone, count(i), sum(i.totalAmount), max(i.invoiceDate)) " +
                            "from Invoice i join i.customer c " +
                            "where year(i.invoiceDate) = :year and month(i.invoiceDate) = :month " +
                            "group by c.customerID, c.customerName, c.phone " +
                            "order by sum(i.totalAmount) desc, count(i) desc, c.customerName asc",
                    TopCustomerDTO.class
            ).setParameter("year", year)
                    .setParameter("month", month)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CustomerInvoiceDetailDTO> findCustomerInvoiceDetailsByMonth(int year, int month, int customerId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select new com.example.storemanagement.model.dto.CustomerInvoiceDetailDTO(" +
                            "i.invoiceID, i.invoiceDate, p.productName, d.quantity, d.unitPrice, " +
                            "(d.unitPrice * d.quantity), i.totalAmount) " +
                            "from Invoice i " +
                            "join i.customer c " +
                            "join i.details d " +
                            "join d.product p " +
                            "where year(i.invoiceDate) = :year " +
                            "and month(i.invoiceDate) = :month " +
                            "and c.customerID = :customerId " +
                            "order by i.invoiceDate desc, i.invoiceID desc, p.productName asc",
                    CustomerInvoiceDetailDTO.class
            ).setParameter("year", year)
                    .setParameter("month", month)
                    .setParameter("customerId", customerId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal decimal) {
            return decimal;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return BigDecimal.ZERO;
    }

    private long toLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        return 0L;
    }
}
