package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.EmployeeDAO;
import com.example.storemanagement.model.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> findAll() {
        // Tạo EntityManager
        EntityManager em = JpaUtil.getEntityManager();

        try {
            // JPQL: "FROM Employee"
            // Lấy toàn bộ dữ liệu từ entity Employee
            // (Employee là tên class, không phải tên bảng)
            return em.createQuery("FROM Employee", Employee.class)
                    .getResultList();

        } finally {
            // Luôn đóng để tránh leak connection
            em.close();
        }
    }

    @Override
    public void save(Employee employee) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Bắt đầu transaction
            tx.begin();

            // persist(): thêm mới dữ liệu (INSERT)
            em.persist(employee);

            // Lưu xuống database
            tx.commit();

        } catch (Exception e) {
            // Nếu lỗi → rollback
            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public void update(Employee employee) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // merge(): cập nhật dữ liệu (UPDATE)
            em.merge(employee);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Tìm entity theo id
            Employee e = em.find(Employee.class, id);

            // Nếu tồn tại thì xóa
            if (e != null) {
                // remove(): DELETE
                em.remove(e);
            }

            tx.commit();

        } catch (Exception e1) {
            if (tx.isActive()) {
                tx.rollback();
            }

            throw e1;

        } finally {
            em.close();
        }
    }

    @Override
    public Employee findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            // find(): tìm theo khóa chính
            // Tương đương SQL: SELECT * FROM employee WHERE id = ?
            return em.find(Employee.class, id);

        } finally {
            // ⚠️ Code cũ của bạn bị thiếu close() ở đây
            em.close();
        }
    }
}