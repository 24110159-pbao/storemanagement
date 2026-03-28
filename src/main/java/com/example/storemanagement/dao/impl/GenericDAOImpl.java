package com.example.storemanagement.dao.impl;


import com.example.storemanagement.dao.GenericDAO;
import com.example.storemanagement.config.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {

    private Class<T> clazz;

    public GenericDAOImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
    }

    @Override
    public T findById(ID id) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.find(clazz, id);
    }

    @Override
    public void save(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
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
            em.merge(entity);
            tx.commit();
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
            T entity = em.find(clazz, id);
            if (entity != null) em.remove(entity);
            tx.commit();
        } finally {
            em.close();
        }
    }
}