package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.InvoiceDetail;
import com.example.storemanagement.model.id.InvoiceDetailId;

import java.util.List;

public interface InvoiceDetailDAO {
    void save(InvoiceDetail detail);

    List<InvoiceDetail> findByInvoiceId(Integer invoiceId);

    void delete(InvoiceDetailId id);
}
