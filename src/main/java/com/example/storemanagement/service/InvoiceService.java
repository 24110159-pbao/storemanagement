package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Invoice;

import java.util.List;

public abstract class InvoiceService {
    public abstract List<Invoice> getAll();

    public abstract void createInvoice(Invoice invoice);

    public abstract List<Invoice> getByCustomer(Integer customerId);
}
