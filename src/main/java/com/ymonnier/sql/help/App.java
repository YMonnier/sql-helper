package com.ymonnier.sql.help;

import com.ymonnier.sql.help.service.CrudServiceBean;
import com.ymonnier.sql.help.service.QueryBuilder;
import com.ymonnier.sql.help.test.MyEntity;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        MyEntity entity = new MyEntity();

        CrudServiceBean<MyEntity> crudServiceBean = new CrudServiceBean<>();
        //crudServiceBean.test(MyEntity.class, "name", 1)
          //      .and("id", 1)


    }
}
