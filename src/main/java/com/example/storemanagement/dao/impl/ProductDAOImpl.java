package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.ProductDAO;
import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
    @Override
    public List<Product> search(String keyword) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Product> list = em.createQuery(
                        "FROM Product p WHERE p.productName LIKE :kw", Product.class)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();
        em.close();
        return list;
    }
    public List<ProductStockDTO> getProductStockByBranch(Integer branchId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT new com.example.storemanagement.model.dto.ProductStockDTO(" +
                            "p.productID, p.productName, p.unitPrice, COALESCE(SUM(b.quantity), 0)) " +
                            "FROM Product p LEFT JOIN p.batches b "
            );

            if (branchId != null) {
                jpql.append("WHERE b.branch.branchID = :branchId ");
            }

            jpql.append("GROUP BY p.productID, p.productName, p.unitPrice");

            TypedQuery<ProductStockDTO> query = em.createQuery(jpql.toString(), ProductStockDTO.class);

            if (branchId != null) {
                query.setParameter("branchId", branchId);
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}