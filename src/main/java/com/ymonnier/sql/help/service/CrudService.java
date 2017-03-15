package com.ymonnier.sql.help.service;

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
    public T find(Class type, String name, Object id);

}
