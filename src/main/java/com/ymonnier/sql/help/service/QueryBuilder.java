package com.ymonnier.sql.help.service;

import com.ymonnier.sql.help.service.utilities.ClassUtil;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.service.
 * File ResultQuery.java.
 * Created by Ysee on 15/03/2017 - 20:43.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class QueryBuilder {
    private Map<String, Object> parameters;
    private String source;
    private final EntityManager entityManager;
    public QueryBuilder(EntityManager entityManager, Class type, String name, Object value) {
        this.parameters = new HashMap<>();
        this.entityManager = entityManager;
        this.parameters.put(name, value);
        this.source = ClassUtil.getTableName(entityManager, type);
    }
}
