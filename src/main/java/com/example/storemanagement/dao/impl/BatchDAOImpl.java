package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.BatchDAO;
import com.example.storemanagement.model.entity.Batch;

import jakarta.persistence.EntityManager;
import java.util.List;

public class BatchDAOImpl implements BatchDAO {

    @Override
    public List<Batch> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Batch> list = em.createQuery("FROM Batch", Batch.class).getResultList();
        em.close();
        return list;
    }

    @Override
    public void save(Batch batch) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(batch);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Batch batch) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(batch);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        Batch b = em.find(Batch.class, id);
        if (b != null) em.remove(b);
        em.getTransaction().commit();
        em.close();
    }
}