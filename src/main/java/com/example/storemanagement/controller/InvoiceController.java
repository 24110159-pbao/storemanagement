package com.example.storemanagement.controller;

import com.example.storemanagement.model.entity.Invoice;
import com.example.storemanagement.service.InvoiceService;
import com.example.storemanagement.service.impl.InvoiceServiceImpl;

import java.util.List;

public class InvoiceController {

    private InvoiceService service = new InvoiceServiceImpl();

    public List<Invoice> getAllInvoices() {
        return service.getAll();
    }

    public void createInvoice(Invoice invoice) {
        service.createInvoice(invoice);
    }

    public List<Invoice> getInvoiceByCustomer(Integer customerId) {
        return service.getByCustomer(customerId);
    }
}

