package com.ymonnier.sql.help.service.utilities;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.service.utilities.
 * File ClassUtil.java.
 * Created by Ysee on 15/03/2017 - 21:39.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class ClassUtil {
    /**
     * Return the table name for a giben entity type.
     * @param em Entity manager.
     * @param entityClass Entity type.
     * @param <T> Generic value
     * @return Table name.
     */
    public static <T> String getTableName(EntityManager em, Class<T> entityClass) {
        Metamodel meta = em.getMetamodel();
        EntityType<T> entityType = meta.entity(entityClass);

        //Check whether @Table annotation is present on the class.
        Table t = entityClass.getAnnotation(Table.class);

        String tableName = (t == null)
                ? entityType.getName().toUpperCase()
                : t.name();
        return tableName;
    }
}
