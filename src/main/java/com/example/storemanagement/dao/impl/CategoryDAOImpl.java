package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.CategoryDAO;
import com.example.storemanagement.model.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import com.example.storemanagement.config.JpaUtil;

public class CategoryDAOImpl implements CategoryDAO {

    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    public List<Category> findAll() {
        return em.createQuery("FROM Category", Category.class).getResultList();
    }

    @Override
    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    @Override
    public void save(Category category) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(category);
        tx.commit();
    }

    @Override
    public void update(Category category) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(category);
        tx.commit();
    }

    @Override
    public void delete(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Category c = em.find(Category.class, id);
        if (c != null) em.remove(c);
        tx.commit();
    }
}