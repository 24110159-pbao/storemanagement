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
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Customer customer) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
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
            Customer c = em.find(Customer.class, id);
            if (c != null) em.remove(c);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Customer findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        Customer c = em.find(Customer.class, id);
        em.close();
        return c;
    }

    @Override
    public List<Customer> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Customer> list = em
                .createQuery("FROM Customer", Customer.class)
                .getResultList();
        em.close();
        return list;
    }
}