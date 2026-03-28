package com.example.storemanagement.dao;

import com.example.storemanagement.model.entity.Batch;
import java.util.List;

public interface BatchDAO {
    List<Batch> findAll();
    void save(Batch batch);
    void update(Batch batch);
    void delete(int id);
}