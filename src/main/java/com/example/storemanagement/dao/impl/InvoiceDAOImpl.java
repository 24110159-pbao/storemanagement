package com.example.storemanagement.dao.impl;


import com.example.storemanagement.dao.InvoiceDAO;
import com.example.storemanagement.model.entity.Invoice;
import com.example.storemanagement.config.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class InvoiceDAOImpl extends GenericDAOImpl<Invoice, Integer> implements InvoiceDAO {

    public InvoiceDAOImpl() {
        super(Invoice.class);
    }

    @Override
    public List<Invoice> findByCustomer(Integer customerId) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery(
                        "SELECT i FROM Invoice i WHERE i.customer.customerID = :cid",
                        Invoice.class)
                .setParameter("cid", customerId)
                .getResultList();
    }
}