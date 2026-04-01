package com.example.storemanagement.dao;

import java.util.List;

public interface GenericDAO<T, ID> {

    // lấy tất cả bản ghi
    // dùng: List<T> list = genericDAO.findAll();
    List<T> findAll();

    // tìm bản ghi theo id
    // dùng: T entity = genericDAO.findById(id);
    T findById(ID id);

    // lưu bản ghi mới vào DB
    // dùng: genericDAO.save(entity);
    void save(T entity);

    // cập nhật bản ghi đã tồn tại
    // dùng: genericDAO.update(entity);
    void update(T entity);

    // xóa bản ghi theo id
    // dùng: genericDAO.delete(id);
    void delete(ID id);
}