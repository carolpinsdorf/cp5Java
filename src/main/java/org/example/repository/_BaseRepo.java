package org.example.repository;

import org.example.entities._BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface _BaseRepo<T extends _BaseEntity>{
    void Insert(T entity);
    void Update(T entity, int id);
    void Delete(int id);
    List<T> GetAll();


}
