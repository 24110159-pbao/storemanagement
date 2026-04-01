package com.example.storemanagement.dao;

import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Invoice;
import java.util.List;

public interface InvoiceDAO extends GenericDAO<Invoice, Integer> {

    // tìm tất cả hóa đơn của khách hàng theo customerId
    // dùng: List<Invoice> invoices = invoiceDAO.findByCustomer(1);
    List<Invoice> findByCustomer(Integer customerId);

    // lấy tồn kho sản phẩm theo chi nhánh
    // dùng: List<ProductStockDTO> stock = invoiceDAO.getProductStockByBranch(1);
    List<ProductStockDTO> getProductStockByBranch(Integer branchId);

    // lưu hóa đơn mới vào DB (override GenericDAO.save nếu cần logic riêng)
    // dùng: invoiceDAO.save(invoice);
    void save(Invoice invoice);
}