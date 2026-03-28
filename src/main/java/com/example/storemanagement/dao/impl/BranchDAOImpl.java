package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.BranchDAO;
import com.example.storemanagement.model.entity.Branch;

import jakarta.persistence.EntityManager;
import java.util.List;

public class BranchDAOImpl implements BranchDAO {

    @Override
    public List<Branch> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery("FROM Branch", Branch.class).getResultList();
    }

    @Override
    public Branch findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.find(Branch.class, id);
    }

    @Override
    public void save(Branch branch) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(branch);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Branch branch) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(branch);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        Branch b = em.find(Branch.class, id);
        if (b != null) em.remove(b);
        em.getTransaction().commit();
        em.close();
    }
}