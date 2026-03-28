package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.BranchProductDAO;
import com.example.storemanagement.model.entity.BranchProduct;

import com.example.storemanagement.model.id.BranchProductId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class BranchProductDAOImpl implements BranchProductDAO {

    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    public BranchProduct findByBranchAndProduct(int branchId, int productId) {
        try {
            return em.createQuery(
                            "SELECT bp FROM BranchProduct bp WHERE bp.branch.branchID = :b AND bp.product.productID = :p",
                            BranchProduct.class)
                    .setParameter("b", branchId)
                    .setParameter("p", productId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(BranchProduct bp) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(bp);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(BranchProduct bp) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(bp);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public BranchProduct findById(int branchId, int productId) {
        EntityManager em = JpaUtil.getEntityManager();

        BranchProductId id = new BranchProductId(branchId, productId);

        return em.find(BranchProduct.class, id);
    }

    @Override
    public void delete(BranchProduct bp) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // phải attach entity vào context trước khi remove
            bp = em.merge(bp);

            em.remove(bp);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
