package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.InvoiceDetailDAO;
import com.example.storemanagement.model.entity.InvoiceDetail;
import com.example.storemanagement.model.id.InvoiceDetailId;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class InvoiceDetailDAOImpl implements InvoiceDetailDAO {

    @Override
    public void save(InvoiceDetail detail) {
        // Tạo EntityManager
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // persist(): thêm mới chi tiết hóa đơn
            em.persist(detail);

            tx.commit();

        } catch (Exception e) {
            // Rollback nếu có lỗi
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            // Đóng EntityManager để tránh leak
            em.close();
        }
    }

    @Override
    public List<InvoiceDetail> findByInvoiceId(Integer invoiceId) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            // JPQL: lấy tất cả chi tiết của một hóa đơn
            // d.invoice.invoiceID: truy cập khóa chính của entity Invoice liên kết
            // :id là parameter binding → tránh SQL injection
            return em.createQuery(
                            "SELECT d FROM InvoiceDetail d WHERE d.invoice.invoiceID = :id",
                            InvoiceDetail.class
                    )
                    .setParameter("id", invoiceId)
                    .getResultList();

        } finally {
            em.close(); // Luôn close để tránh leak
        }
    }

    @Override
    public void delete(InvoiceDetailId id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Tìm entity theo composite key (InvoiceDetailId)
            InvoiceDetail d = em.find(InvoiceDetail.class, id);

            // Nếu tồn tại → remove()
            if (d != null) {
                em.remove(d);
            }

            tx.commit();

        } catch (Exception e) {
            // Rollback nếu lỗi
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }
}