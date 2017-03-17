package com.ymonnier.sql.help.test;

import com.ymonnier.sql.help.generator.annotations.Attr;
import com.ymonnier.sql.help.generator.annotations.Extends;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.test.
 * File MyEntity.java.
 * Created by Ysee on 15/03/2017 - 13:49.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
@Extends
@Entity
public class MyEntity {
    @Id
    @Attr
    Long id;
    @Attr
    String name;
    String address;

    void test() {

    }
}
