package com.crm.persistence.repository;

public interface CrudRepository<T> {
    void save(T entity);
    T findById(String id);
    void update(T entity);
    void delete(String id);
}
