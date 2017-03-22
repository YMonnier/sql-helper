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

    public EntityManager entityManager;

    public CrudServiceBean() {
        this.entityManager = EntityManagerFactory.get();
    }

    public CrudServiceBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Save an entity into the database.
     *
     * @param object Entity object.
     * @return The entity updated(@ID).
     */
    @Override
    public T save(T object) {
        entityManager.persist(object);
        entityManager.flush();
        entityManager.refresh(object);
        return object;
    }

    /**
     * Update an entity into the database.
     *
     * @param object Entity object.
     * @return The entity updated.
     */
    @Override
    public T update(T object) {
        return entityManager.merge(object);
    }

    /**
     * Delete an entity into the database.
     *
     * @param object Entity object.
     */
    @Override
    public void delete(T object) {
        entityManager.remove(object);
    }

    /**
     * Create a QueryBuilder for a named query.
     *
     * @param namedQueryName The string query name.
     * @return QueryBuilder object. @see QueryBuilder
     */
    public QueryBuilder<T> findWithNamedQuery(String namedQueryName) {
        Query query = entityManager.createNamedQuery(namedQueryName);
        return new QueryBuilder<>(query);
    }

    /**
     * Create a QueryBuilder for a specific query.
     *
     * @param query The string query (native SQL or JPQL).
     * @return QueryBuilder object. @see QueryBuilder
     */
    public QueryBuilder<T> findWithQuery(String query) {
        return new QueryBuilder<>(entityManager.createQuery(query));
    }

    /**
     * Close the EntityManager connection.
     */
    @Override
    public void close() {
        EntityManagerFactory.release(entityManager);
    }
}
