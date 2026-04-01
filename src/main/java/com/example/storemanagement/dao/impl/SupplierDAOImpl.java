package com.example.storemanagement.dao.impl;

import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.dao.SupplierDAO;
import com.example.storemanagement.model.entity.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    /**
     * Lấy danh sách tất cả các nhà cung cấp từ cơ sở dữ liệu.
     * Sử dụng câu lệnh JPQL "FROM Supplier" để truy vấn toàn bộ thực thể.
     */
    @Override
    public List<Supplier> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        return em.createQuery("FROM Supplier", Supplier.class).getResultList();
    }

    /**
     * Tìm kiếm một nhà cung cấp cụ thể theo mã ID.
     * @param id Mã định danh của nhà cung cấp cần tìm.
     * @return Đối tượng Supplier nếu tìm thấy, ngược lại trả về null.
     */
    @Override
    public Supplier findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.find(Supplier.class, id);
    }

    /**
     * Thêm mới một nhà cung cấp vào cơ sở dữ liệu.
     * Sử dụng Transaction để đảm bảo tính toàn vẹn dữ liệu khi thực hiện persist.
     * @param supplier Đối tượng nhà cung cấp mới cần lưu.
     */
    @Override
    public void save(Supplier supplier) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // Bắt đầu giao dịch
            em.persist(supplier); // Lưu thực thể mới
            tx.commit(); // Xác nhận lưu thay đổi
        } catch (Exception e) {
            tx.rollback(); // Hoàn tác nếu có lỗi xảy ra
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật thông tin của một nhà cung cấp đã tồn tại.
     * @param supplier Đối tượng nhà cung cấp chứa thông tin mới.
     */
    @Override
    public void update(Supplier supplier) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(supplier); // Cập nhật trạng thái thực thể vào DB
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Xóa một nhà cung cấp khỏi cơ sở dữ liệu dựa trên ID.
     * @param id Mã định danh của nhà cung cấp cần xóa.
     */
    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // Tìm thực thể trước khi xóa để đảm bảo thực thể nằm trong vùng quản lý (managed)
            Supplier s = em.find(Supplier.class, id);
            if (s != null) {
                em.remove(s); // Thực hiện xóa
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
}
