package com.example.storemanagement.dao.impl;

import com.example.storemanagement.dao.ProductDAO;
import com.example.storemanagement.model.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final EntityManager em;

    public ProductDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    @Override
    public void update(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Integer id) {
        em.getTransaction().begin();
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
        em.getTransaction().commit();
    }

    @Override
    public Product findById(Integer id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    @Override
    public List<Product> findByName(String name) {
        TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(:name)",
                Product.class
        );
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}