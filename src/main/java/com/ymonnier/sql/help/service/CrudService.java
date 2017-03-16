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
    public T save(T object);

    public T update(T object);

    public void delete(T object);

    public List<T> findWithNamedQuery(String queryName);

    public List<T> findWithNamedQuery(String queryName, int limit);

    public List<T> findWithNamedQuery(String queryName, Map parameters);

    public List<T> findWithNamedQuery(String queryName, Map parameters, int resultLimit);

    public void close();
}
