package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.ProductDAO;
import com.example.storemanagement.model.entity.Product;

import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public List<Product> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery("FROM Product", Product.class).getResultList();
    }

    @Override
    public void save(Product product) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    @Override
    public void update(Product product) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        Product p = em.find(Product.class, id);
        if (p != null) em.remove(p);
        em.getTransaction().commit();
    }

    @Override
    public Product findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.find(Product.class, id);
    }
}