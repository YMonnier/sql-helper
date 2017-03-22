package com.ymonnier.sql.help.generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Ysee on 15/03/2017 - 16:37.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */

/**
 * Attr annotation can be apply on a Field Element.
 * This Annotation allows to create the 'findByATTR' method.
 * @see Extends
 * @see com.ymonnier.sql.help.service.CrudService
 * @see com.ymonnier.sql.help.service.CrudServiceBean
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.SOURCE)
public @interface Attr {
}
