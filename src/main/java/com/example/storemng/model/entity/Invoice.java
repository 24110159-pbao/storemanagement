package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "INVOICE")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceID;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "BranchID")
    private Branch branch;

    private LocalDate invoiceDate;

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<InvoiceDetail> details = new ArrayList<>();

    public Invoice() {}

    public Long getInvoiceID() { return invoiceID; }
    public Customer getCustomer() { return customer; }
    public Employee getEmployee() { return employee; }
    public Branch getBranch() { return branch; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<InvoiceDetail> getDetails() { return details; }

    public void setInvoiceID(Long invoiceID) { this.invoiceID = invoiceID; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setBranch(Branch branch) { this.branch = branch; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setDetails(List<InvoiceDetail> details) { this.details = details; }

    @Override
    public String toString() {
        return "Invoice " + invoiceID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice that)) return false;
        return invoiceID != null && invoiceID.equals(that.invoiceID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
