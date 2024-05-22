package com.example.iss.repo;

import com.example.iss.domain.Entity;

import java.util.Collection;

public interface Repo<T extends Entity<Tid>, Tid> {
    void add(T elem);
    void delete(Integer id);
    void update(T elem, Tid id);
    T findById(Tid id);
    Iterable<T> findAll();
    Collection<T> getAll();

}