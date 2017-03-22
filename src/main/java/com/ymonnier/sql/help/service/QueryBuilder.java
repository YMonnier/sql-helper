package com.ymonnier.sql.help.service;


import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.service.
 * File ResultQuery.java.
 * Created by Ysee on 15/03/2017 - 20:43.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class QueryBuilder<T> {
    /**
     * Parameters query.
     */
    private Map<String, Object> parameters;

    /**
     * limitation of tuple number
     */
    private int limit;

    /**
     * Current query created
     * by the user (native sql, jpql, ...).
     */
    private Query query;

    public QueryBuilder(Query query) {
        this.parameters = new HashMap<>();
        this.limit = 0;
        this.query = query;
    }

    /**
     * Set a parameter to the current query.
     *
     * @param name  key value parameter
     * @param value value parameter
     * @return `QueryBuilder` object
     */
    public QueryBuilder<T> where(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    /**
     * Set a parameter to the current query.
     *
     * @param name  key value parameter
     * @param value value parameter
     * @return `QueryBuilder` object
     */
    public QueryBuilder<T> and(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    /**
     * Set the limitation of tuple number
     *
     * @param limit max tuple number
     * @return `QueryBuilder` object
     */
    public QueryBuilder<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Return a list of generic object.
     *
     * @return list of generic object.
     */
    public List list() {
        apply();
        return query.getResultList();
    }

    /**
     * Return a single object.
     *
     * @return Single generic object.
     */
    public T first() {
        apply();
        return (T) query.getSingleResult();
    }

    /**
     * Apply all query settings
     * like the limitation of tuple number
     * and parameters
     */
    private void apply() {
        Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        if (limit > 0)
            query.setMaxResults(limit);
        for (Map.Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
