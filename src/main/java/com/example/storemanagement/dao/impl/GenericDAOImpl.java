package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.GenericDAO;
import com.example.storemanagement.config.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {

    // clazz dùng để lưu kiểu của entity (VD: Customer.class, Employee.class)
    private Class<T> clazz;

    // Constructor nhận vào class của entity
    public GenericDAOImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> findAll() {
        // Tạo EntityManager
        EntityManager em = JpaUtil.getEntityManager();

        try {
            // JPQL động:
            // clazz.getSimpleName() → lấy tên class (VD: "Customer")
            // => "FROM Customer", "FROM Employee", ...
            return em.createQuery("FROM " + clazz.getSimpleName(), clazz)
                    .getResultList();

        } finally {
            // Luôn đóng để tránh leak connection
            em.close();
        }
    }

    @Override
    public T findById(ID id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            // find(): tìm theo khóa chính
            return em.find(clazz, id);

        } finally {
            em.close();
        }
    }

    @Override
    public void save(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Bắt đầu transaction
            tx.begin();

            // persist(): INSERT
            em.persist(entity);

            tx.commit();

        } catch (Exception e) {
            // Nếu lỗi → rollback
            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public void update(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // merge(): UPDATE
            em.merge(entity);

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
    public void delete(ID id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Tìm entity theo id
            T entity = em.find(clazz, id);

            // Nếu tồn tại thì xóa
            if (entity != null) {
                // remove(): DELETE
                em.remove(entity);
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