package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.EmployeeDAO;
import com.example.storemanagement.model.entity.Employee;

import jakarta.persistence.EntityManager;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery("FROM Employee", Employee.class).getResultList();
    }

    @Override
    public void save(Employee employee) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Employee employee) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(employee);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        Employee e = em.find(Employee.class, id);
        if (e != null) em.remove(e);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Employee findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.find(Employee.class, id);
    }
}