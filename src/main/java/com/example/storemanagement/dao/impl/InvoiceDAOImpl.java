package com.example.storemanagement.dao.impl;


import com.example.storemanagement.dao.InvoiceDAO;
import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Invoice;
import com.example.storemanagement.config.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class InvoiceDAOImpl extends GenericDAOImpl<Invoice, Integer> implements InvoiceDAO {

    public InvoiceDAOImpl() {
        super(Invoice.class);
    }
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    public List<Invoice> findByCustomer(Integer customerId) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery(
                        "SELECT i FROM Invoice i WHERE i.customer.customerID = :cid",
                        Invoice.class)
                .setParameter("cid", customerId)
                .getResultList();
    }
    @Override
    public List<ProductStockDTO> getProductStockByBranch(Integer branchId) {
        EntityManager em = JpaUtil.getEntityManager();

        String jpql = """
            SELECT new com.example.storemanagement.model.dto.ProductStockDTO(
                p.productID,
                p.productName,
                p.unitPrice,
                COALESCE(SUM(b.quantity), 0)
            )
            FROM Product p
            LEFT JOIN Batch b ON p.productID = b.product.productID
        """;

        if (branchId != null) {
            jpql += " AND b.branch.branchID = :branchId";
        }

        jpql += " GROUP BY p.productID, p.productName, p.unitPrice";

        var query = em.createQuery(jpql, ProductStockDTO.class);

        if (branchId != null) {
            query.setParameter("branchId", branchId);
        }

        return query.getResultList();
    }

    @Override
    public void save(Invoice invoice) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(invoice);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}