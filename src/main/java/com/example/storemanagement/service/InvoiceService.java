package com.example.storemanagement.service;

import com.example.storemanagement.model.dto.InvoiceInputDTO;
import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Invoice;

import java.util.List;

public interface  InvoiceService {
    public abstract List<Invoice> getAll();

    public abstract void createInvoice(Invoice invoice);

    public abstract List<Invoice> getByCustomer(Integer customerId);

    void createInvoice(InvoiceInputDTO dto);
    // Kiểm tra số lượng tồn kho của một sản phẩm tại một chi nhánh
    boolean checkStock(Integer branchId, Integer productId, int quantity);

}
