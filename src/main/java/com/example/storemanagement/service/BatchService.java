package com.example.storemanagement.service;

import com.example.storemanagement.model.entity.Batch;
import java.util.List;

public interface BatchService {
    List<Batch> getAll();
    void add(Batch batch);
    void update(Batch batch);
    void delete(int id);
    Batch getById(int id);
}