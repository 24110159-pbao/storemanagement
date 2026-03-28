package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.SupplierDAO;
import com.example.storemanagement.model.entity.Supplier;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public List<Supplier> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery("FROM Supplier", Supplier.class).getResultList();
    }

    @Override
    public Supplier findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.find(Supplier.class, id);
    }

    @Override
    public void save(Supplier supplier) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(supplier);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Supplier supplier) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(supplier);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Supplier s = em.find(Supplier.class, id);
            if (s != null) em.remove(s);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
}