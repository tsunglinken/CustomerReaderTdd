package com.customer.tdd.repository;

import java.util.List;

public interface IRepository<T> {
    T findById(Integer id);
    List<T> findAll();
    void save(T t);
}
