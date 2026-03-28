package com.example.storemanagement.service.impl;


import com.example.storemanagement.dao.InvoiceDAO;
import com.example.storemanagement.dao.impl.InvoiceDAOImpl;
import com.example.storemanagement.model.entity.Invoice;
import com.example.storemanagement.service.InvoiceService;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceServiceImpl extends InvoiceService {

    private InvoiceDAO dao = new InvoiceDAOImpl();

    @Override
    public List<Invoice> getAll() {
        return dao.findAll();
    }

    @Override
    public void createInvoice(Invoice invoice) {

        // 🔥 BUSINESS LOGIC QUAN TRỌNG
        BigDecimal total = BigDecimal.ZERO;

        if (invoice.getDetails() != null) {
            for (var d : invoice.getDetails()) {
                BigDecimal line = d.getUnitPrice()
                        .multiply(BigDecimal.valueOf(d.getQuantity()));
                total = total.add(line);
            }
        }

        invoice.setTotalAmount(total);

        dao.save(invoice);
    }

    @Override
    public List<Invoice> getByCustomer(Integer customerId) {
        return dao.findByCustomer(customerId);
    }
}
