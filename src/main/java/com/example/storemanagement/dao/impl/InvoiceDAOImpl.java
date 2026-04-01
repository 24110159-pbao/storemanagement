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

    @Override
    public List<Invoice> findByCustomer(Integer customerId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // JPQL: tìm tất cả hóa đơn của khách hàng theo id
            return em.createQuery(
                            "SELECT i FROM Invoice i WHERE i.customer.customerID = :cid",
                            Invoice.class)
                    .setParameter("cid", customerId)
                    .getResultList();
        } finally {
            em.close(); // Đảm bảo không leak connection
        }
    }

    @Override
    public List<ProductStockDTO> getProductStockByBranch(Integer branchId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // JPQL: lấy tổng số lượng sản phẩm theo branch
            // Sử dụng constructor expression để trả về ProductStockDTO
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

            // Nếu branchId != null thì lọc theo chi nhánh
            if (branchId != null) {
                jpql += " AND b.branch.branchID = :branchId";
            }

            jpql += " GROUP BY p.productID, p.productName, p.unitPrice";

            var query = em.createQuery(jpql, ProductStockDTO.class);

            if (branchId != null) {
                query.setParameter("branchId", branchId);
            }

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public void save(Invoice invoice) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // persist(): thêm mới hóa đơn
            em.persist(invoice);

            tx.commit();

        } catch (Exception e) {
            // Rollback nếu lỗi
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e; // ném tiếp lỗi ra ngoài để debug
        } finally {
            em.close();
        }
    }
}