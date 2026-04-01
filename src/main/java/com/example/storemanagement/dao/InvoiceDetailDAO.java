package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.InvoiceDetail;
import com.example.storemanagement.model.id.InvoiceDetailId;
import java.util.List;

public interface InvoiceDetailDAO {

    // lưu chi tiết hóa đơn mới vào DB
    // dùng: invoiceDetailDAO.save(detail);
    void save(InvoiceDetail detail);

    // tìm tất cả chi tiết hóa đơn theo invoiceId
    // dùng: List<InvoiceDetail> list = invoiceDetailDAO.findByInvoiceId(1);
    List<InvoiceDetail> findByInvoiceId(Integer invoiceId);

    // xóa chi tiết hóa đơn theo composite id
    // dùng: invoiceDetailDAO.delete(new InvoiceDetailId(invoiceId, productId));
    void delete(InvoiceDetailId id);
}