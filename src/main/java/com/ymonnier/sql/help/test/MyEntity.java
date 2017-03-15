package com.ymonnier.sql.help.test;

import com.ymonnier.sql.help.generator.annotations.Attr;
import com.ymonnier.sql.help.generator.annotations.Extends;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.test.
 * File MyEntity.java.
 * Created by Ysee on 15/03/2017 - 13:49.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Extends
public class MyEntity {
    @Attr
    Long id;
    @Attr
    String name;
    String address;

    void test() {

    }
}
