package com.example.storemanagement.model.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO dùng để nhận dữ liệu đầu vào khi tạo hóa đơn.
 * Thường được gửi từ client (API request).
 */
public class InvoiceInputDTO {

    // ID khách hàng mua hàng
    private Integer customerId;

    // ID nhân viên lập hóa đơn
    private Integer employeeId;

    // ID chi nhánh nơi tạo hóa đơn
    private Integer branchId;

    // Ngày lập hóa đơn
    private LocalDate invoiceDate;

    // Danh sách các sản phẩm trong hóa đơn
    private List<InvoiceItemDTO> items;

    /**
     * @return ID khách hàng
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId ID khách hàng
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return ID nhân viên
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId ID nhân viên
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return ID chi nhánh
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * @param branchId ID chi nhánh
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * @return Ngày lập hóa đơn
     */
    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * @param invoiceDate Ngày lập hóa đơn
     */
    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @return Danh sách sản phẩm trong hóa đơn
     */
    public List<InvoiceItemDTO> getItems() {
        return items;
    }

    /**
     * @param items Danh sách các dòng sản phẩm (mỗi dòng là 1 item)
     */
    public void setItems(List<InvoiceItemDTO> items) {
        this.items = items;
    }
}