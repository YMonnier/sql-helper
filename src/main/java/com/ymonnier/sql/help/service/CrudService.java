package com.ymonnier.sql.help.service;

import java.util.List;
import java.util.Map;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.service.
 * File CrudService.java.
 * Created by Ysee on 15/03/2017 - 19:16.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public interface CrudService<T> {
    /**
     * Save an entity into the database.
     *
     * @param object Entity object.
     * @return The entity updated(@ID).
     */
    public T save(T object);

    /**
     * Update an entity into the database.
     *
     * @param object Entity object.
     * @return The entity updated.
     */
    public T update(T object);

    /**
     * Delete an entity into the database.
     *
     * @param object Entity object.
     */
    public void delete(T object);

    /**
     * Create a QueryBuilder for a named query.
     *
     * @param namedQueryName The string query name.
     * @return QueryBuilder object. @see QueryBuilder
     */
    public QueryBuilder<T> findWithNamedQuery(String namedQueryName);

    /**
     * Create a QueryBuilder for a specific query.
     *
     * @param query The string query (native SQL or JPQL).
     * @return QueryBuilder object. @see QueryBuilder
     */
    public QueryBuilder<T> findWithQuery(String query);

    /**
     * Close the EntityManager connection.
     */
    public void close();
}
