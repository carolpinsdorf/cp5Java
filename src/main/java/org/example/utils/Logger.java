package org.example.utils;

import org.example.entities.Artist;
import org.example.entities._BaseEntity;

public interface Logger<T extends _BaseEntity> {
    void logCreate(T entity);
    void logUpdate(T entity);
    void logDelete(int id);

}
