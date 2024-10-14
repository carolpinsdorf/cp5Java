package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.example.entities.Artist;
import org.example.entities._BaseEntity;

public class Log4jLogger <T extends _BaseEntity> extends _BaseEntity implements Logger<T> {

    //declarar o logger
    private final org.apache.logging.log4j.Logger logger;

    //inicialiar o logger
    public Log4jLogger(Class<T> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    @Override
    public void logCreate(T entity) {
        logger.info("Create: " + entity);
    }

    @Override
    public void logUpdate(T entity) {
        logger.info("Update: " + entity);
    }

    @Override
    public void logDelete(int id) {
        logger.info("Delete: " + id);
    }
}
