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
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Attr {
}
