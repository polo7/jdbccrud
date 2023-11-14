package dev.lesechko.jdbccrud.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll();
    T getById(ID id);
    boolean save(T t);
    boolean update(T t);
    boolean deleteById(ID id);
}
