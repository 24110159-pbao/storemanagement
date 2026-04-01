package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Batch;
import java.util.List;

public interface BatchDAO {

    // lấy tất cả batch
    // dùng: List<Batch> list = batchDAO.findAll();
    List<Batch> findAll();

    // lưu batch mới vào DB
    // dùng: batchDAO.save(batch);
    void save(Batch batch);

    // cập nhật batch đã tồn tại
    // dùng: batchDAO.update(batch);
    void update(Batch batch);

    // xóa batch theo id
    // dùng: batchDAO.delete(1);
    void delete(int id);
}