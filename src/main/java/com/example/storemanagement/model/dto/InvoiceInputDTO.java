package com.example.storemanagement.model.dto;

import java.time.LocalDate;
import java.util.List;

import java.util.Date;

public class InvoiceInputDTO {
    private Integer customerId;
    private Integer employeeId;
    private Integer branchId;
    private LocalDate  invoiceDate;
    private List<InvoiceItemDTO> items;

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public Integer getBranchId() { return branchId; }
    public void setBranchId(Integer branchId) { this.branchId = branchId; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public List<InvoiceItemDTO> getItems() { return items; }
    public void setItems(List<InvoiceItemDTO> items) { this.items = items; }
}