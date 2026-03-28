package com.example.storemanagement.service.impl;

import com.example.storemanagement.dao.BatchDAO;
import com.example.storemanagement.dao.impl.BatchDAOImpl;
import com.example.storemanagement.model.entity.Batch;
import com.example.storemanagement.service.BatchService;

import java.util.List;

public class BatchServiceImpl implements BatchService {

    private BatchDAO dao = new BatchDAOImpl();

    @Override
    public List<Batch> getAll() {
        return dao.findAll();
    }

    @Override
    public void add(Batch batch) {
        dao.save(batch);
    }

    @Override
    public void update(Batch batch) {
        dao.update(batch);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public Batch getById(int id) {
        return dao.findById(id);
    }
}