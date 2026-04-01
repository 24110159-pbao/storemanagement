package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.CategoryDAO;
import com.example.storemanagement.model.entity.Category;
import com.example.storemanagement.config.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public List<Category> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // JPQL: lấy toàn bộ Category
            // "Category" là tên entity class (không phải bảng)
            return em.createQuery("FROM Category", Category.class)
                    .getResultList();
        } finally {
            // Luôn đóng để tránh leak connection
            em.close();
        }
    }

    @Override
    public Category findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Tìm theo khóa chính
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Category category) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // INSERT
            em.persist(category);

            tx.commit();
        } catch (Exception e) {
            // Nếu có lỗi → rollback
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e; // ném lỗi ra ngoài để biết có vấn đề
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Category category) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // UPDATE
            em.merge(category);

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

            // Tìm entity trước khi xóa
            Category c = em.find(Category.class, id);

            if (c != null) {
                // DELETE
                em.remove(c);
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