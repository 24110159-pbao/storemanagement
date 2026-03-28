package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.SupplierDAO;
import com.example.storemanagement.model.entity.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    private final EntityManager em;

    public SupplierDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Supplier supplier) {
        em.getTransaction().begin();
        em.persist(supplier);
        em.getTransaction().commit();
    }

    @Override
    public void update(Supplier supplier) {
        em.getTransaction().begin();
        em.merge(supplier);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        em.getTransaction().begin();
        Supplier supplier = em.find(Supplier.class, id);
        if (supplier != null) {
            em.remove(supplier);
        }
        em.getTransaction().commit();
    }

    @Override
    public Supplier findById(Integer id) {
        return em.find(Supplier.class, id);
    }

    @Override
    public List<Supplier> findAll() {
        return em.createQuery("SELECT s FROM Supplier s", Supplier.class)
                .getResultList();
    }

    @Override
    public List<Supplier> findByName(String name) {
        TypedQuery<Supplier> query = em.createQuery(
                "SELECT s FROM Supplier s WHERE LOWER(s.supplierName) LIKE LOWER(:name)",
                Supplier.class
        );
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}