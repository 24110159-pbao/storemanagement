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
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(detail);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<InvoiceDetail> findByInvoiceId(Integer invoiceId) {
        EntityManager em = JpaUtil.getEntityManager();

        List<InvoiceDetail> list = em.createQuery(
                        "SELECT d FROM InvoiceDetail d WHERE d.invoice.invoiceID = :id",
                        InvoiceDetail.class
                ).setParameter("id", invoiceId)
                .getResultList();

        em.close();
        return list;
    }

    @Override
    public void delete(InvoiceDetailId id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            InvoiceDetail d = em.find(InvoiceDetail.class, id);
            if (d != null) em.remove(d);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}