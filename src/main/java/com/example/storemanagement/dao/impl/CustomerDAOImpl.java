package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.CustomerDAO;
import com.example.storemanagement.model.entity.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void save(Customer customer) {
        // Tạo EntityManager để thao tác DB
        EntityManager em = JpaUtil.getEntityManager();

        // Lấy transaction
        EntityTransaction tx = em.getTransaction();

        try {
            // Bắt đầu transaction
            tx.begin();

            // persist(): thêm mới dữ liệu (INSERT)
            em.persist(customer);

            // Commit để lưu vào DB
            tx.commit();

        } catch (Exception e) {
            // Nếu có lỗi → rollback để tránh sai dữ liệu
            if (tx.isActive()) {
                tx.rollback();
            }

            // In lỗi ra để debug
            e.printStackTrace();

            // Ném lỗi ra ngoài (best practice)
            throw e;

        } finally {
            // Luôn đóng EntityManager để tránh leak connection
            em.close();
        }
    }

    @Override
    public void update(Customer customer) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // merge(): cập nhật dữ liệu (UPDATE)
            em.merge(customer);

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

            // Tìm theo id
            Customer c = em.find(Customer.class, id);

            // Nếu tồn tại thì xóa
            if (c != null) {
                // remove(): DELETE
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

    @Override
    public Customer findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            // find(): tìm theo khóa chính
            // Tương đương SQL: SELECT * FROM customer WHERE id = ?
            return em.find(Customer.class, id);

        } finally {
            // Đảm bảo đóng
            em.close();
        }
    }

    @Override
    public List<Customer> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            // JPQL: "FROM Customer"
            // Lấy toàn bộ dữ liệu từ entity Customer
            // (Customer là tên class, không phải bảng)
            return em.createQuery("FROM Customer", Customer.class)
                    .getResultList();

        } finally {
            em.close();
        }
    }
}