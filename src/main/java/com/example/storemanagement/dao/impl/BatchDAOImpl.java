package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.BatchDAO;
import com.example.storemanagement.model.entity.Batch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class BatchDAOImpl implements BatchDAO {

    @Override
    public List<Batch> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // JPQL: "FROM Batch"
            // Lấy toàn bộ dữ liệu từ entity Batch
            return em.createQuery("FROM Batch", Batch.class)
                    .getResultList();
        } finally {
            // Đảm bảo luôn đóng EntityManager
            em.close();
        }
    }

    @Override
    public void save(Batch batch) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // INSERT dữ liệu mới
            em.persist(batch);

            tx.commit();
        } catch (Exception e) {
            // Nếu có lỗi → rollback
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Batch batch) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // UPDATE dữ liệu
            em.merge(batch);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Tìm entity theo id trước khi xóa
            Batch b = em.find(Batch.class, id);

            if (b != null) {
                // DELETE
                em.remove(b);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}