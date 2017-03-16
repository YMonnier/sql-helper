package com.ymonnier.sql.help.service;

import com.ymonnier.sql.help.service.fatory.EntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.service.
 * File CrudServiceBean.java.
 * Created by Ysee on 15/03/2017 - 19:39.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class CrudServiceBean<T> implements CrudService<T> {

    private EntityManager entityManager;

    public CrudServiceBean() {
        this.entityManager = EntityManagerFactory.get();
    }

    @Override
    public T save(T object) {
        entityManager.persist(object);
        entityManager.flush();
        entityManager.refresh(object);
        return object;
    }

    @Override
    public T update(T object) {
        return entityManager.merge(object);
    }

    @Override
    public void delete(T object) {
        entityManager.remove(object);
    }

    @Override
    public List findWithNamedQuery(String queryName) {
        return entityManager.createNamedQuery(queryName).getResultList();
    }

    @Override
    public List findWithNamedQuery(String queryName, int limit) {
        return entityManager.createNamedQuery(queryName)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List findWithNamedQuery(String queryName, Map parameters) {
        return findWithNamedQuery(queryName, parameters, 0);
    }

    @Override
    public List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {
        Set<Map.Entry> rawParameters = parameters.entrySet();

        Query query = entityManager.createNamedQuery(namedQueryName);
        if (resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry entry : rawParameters) {

            query.setParameter((Integer) entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @Override
    public void close() {
        EntityManagerFactory.release(entityManager);
    }
}
