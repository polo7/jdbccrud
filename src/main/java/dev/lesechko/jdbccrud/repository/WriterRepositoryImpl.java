package dev.lesechko.jdbccrud.repository;

import dev.lesechko.jdbccrud.model.Writer;

import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    @Override
    public boolean save(Writer writer) {
        return false;
    }

    @Override
    public List<Writer> getAll() {
        return null;
    }

    @Override
    public Writer getById(Long aLong) {
        return null;
    }

    @Override
    public boolean update(Writer writer) {
        return false;
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
