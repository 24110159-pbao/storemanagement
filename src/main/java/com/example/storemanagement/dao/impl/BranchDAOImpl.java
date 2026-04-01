package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.BranchDAO;
import com.example.storemanagement.model.entity.Branch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class BranchDAOImpl implements BranchDAO {

    @Override
    public List<Branch> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // JPQL: "FROM Branch"
            // Lấy toàn bộ dữ liệu từ entity Branch
            return em.createQuery("FROM Branch", Branch.class)
                    .getResultList();
        } finally {
            // Luôn đóng để tránh leak connection
            em.close();
        }
    }

    @Override
    public Branch findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Tìm theo khóa chính (id)
            return em.find(Branch.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Branch branch) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // INSERT dữ liệu mới
            em.persist(branch);

            tx.commit();
        } catch (Exception e) {
            // Nếu lỗi thì rollback
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Branch branch) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // UPDATE dữ liệu
            em.merge(branch);

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
            Branch b = em.find(Branch.class, id);

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