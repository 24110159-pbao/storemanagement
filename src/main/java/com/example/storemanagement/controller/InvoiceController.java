package com.example.storemanagement.controller;

import com.example.storemanagement.model.dto.InvoiceInputDTO;
import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Invoice;
import com.example.storemanagement.service.InvoiceService;
import com.example.storemanagement.service.impl.InvoiceServiceImpl;

import java.util.List;

public class InvoiceController {

    // service xử lý logic Invoice
    private InvoiceService service = new InvoiceServiceImpl();

    // lấy tất cả hóa đơn
    // dùng: invoiceController.getAllInvoices();
    public List<Invoice> getAllInvoices() {
        return service.getAll();
    }

    // tạo hóa đơn từ entity
    // dùng: invoiceController.createInvoice(invoice);
    public void createInvoice(Invoice invoice) {
        service.createInvoice(invoice);
    }

    // lấy hóa đơn theo customer id
    // dùng: invoiceController.getInvoiceByCustomer(1);
    public List<Invoice> getInvoiceByCustomer(Integer customerId) {
        return service.getByCustomer(customerId);
    }

    // tạo hóa đơn từ DTO (cách dùng phổ biến hơn)
    // dùng: invoiceController.createInvoice(dto);
    public void createInvoice(InvoiceInputDTO dto) {
        service.createInvoice(dto);
    }

    // kiểm tra tồn kho trước khi tạo hóa đơn
    // dùng: invoiceController.checkStock(1L, 2L, 10);
    public boolean checkStock(Long branchId, Long productId, int quantity) {
        return service.checkStock(branchId.intValue(), productId.intValue(), quantity);
    }
}