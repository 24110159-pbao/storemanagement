package com.example.storemanagement.dao;



import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Invoice;
import java.util.List;

public interface InvoiceDAO extends GenericDAO<Invoice, Integer> {
    List<Invoice> findByCustomer(Integer customerId);
    List<ProductStockDTO> getProductStockByBranch(Integer branchId);
    void save(Invoice invoice);
}
