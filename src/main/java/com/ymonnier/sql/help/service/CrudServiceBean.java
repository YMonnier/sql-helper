package com.ymonnier.sql.help.service;

import javax.persistence.EntityManager;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.service.
 * File CrudServiceBean.java.
 * Created by Ysee on 15/03/2017 - 19:39.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class CrudServiceBean<T> implements CrudService<T> {

    EntityManager entityManager;

    public CrudServiceBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T save(T object) {
        entityManager.persist(object);
        entityManager.flush();
        entityManager.refresh(object);

        return object;
    }

    @Override
    public T find(Class type, String name, Object id) {
        return null;
    }
}
