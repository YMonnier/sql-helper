# sql-helper

[![Java](https://img.shields.io/badge/Java-8-blue.svg?style=flat)](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)

JPA Annotation Helper

CRUD + findById methods generator depending on attributes.

```java

  @Target(value = ElementType.TYPE)
  @Retention(value = RetentionPolicy.SOURCE)
  public @interface Extends {}
  
  @Target(value = ElementType.FIELD)
  @Retention(value = RetentionPolicy.SOURCE)
  public @interface Attr {}
```

## How to use it

Before to use ORM operations, initialize the `EntityManagerFactory`:

* `EntityManagerFactory.init("persistence-unit name");` (from your `persistence.xml`)

### Your entity

```java
  @Entity
  @Extends
  public class MyEntity {
      @Attr
      @Id
      @GeneratedValue
      Long id; // generate findById method

      @Attr
      String name; // generate findByName method

      @Attr
      String address; // generate findByAddress method
  }
```

Will generate: 

```java
  public class _MyEntity {

      private final CrudServiceBean<MyEntity> service = new CrudServiceBean<MyEntity>();

      public MyEntity findById(java.lang.Long id) {
          return null;
      }

      public MyEntity findByName(java.lang.String name) {
          return null;
      }

      public MyEntity findByAddress(java.lang.String address) {
          return null;
      }

      public EntityManager getEntityManager() {
          return service.entityManager;
      }

      public MyEntity save(MyEntity object) {
          return service.save(object);
      }

      public MyEntity update(MyEntity object) {
          return service.update(object);
      }

      public void delete(MyEntity object) {
          service.delete(object);
      }

      public List findWithNamedQuery(String queryName) {
          return service.findWithNamedQuery(queryName);
      }

      public List findWithNamedQuery(String queryName, int limit) {
          return service.findWithNamedQuery(queryName, limit);
      }

      public List findWithNamedQuery(String queryName, Map parameters) {
          return service.findWithNamedQuery(queryName, parameters, 0);
      }

      public List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {
          return service.findWithNamedQuery(namedQueryName, parameters, resultLimit);
      }

      public void close() {
          service.close();
      }   
  }
```

Main.java

```java
  public class App {
    public static void main(String[] args) {
        EntityManagerFactory.init("testpostgresqllocal");


        _MyEntity service = new _MyEntity();
        
        MyEntity entity = new MyEntity();
        entity.name = "John";
        entity.address = "10 street...";

        EntityTransaction transac = service.getEntityManager().getTransaction();
        transac.begin();
        service.save(entity);
        transac.commit();
    }
}
```

Contributor
------------

* [@YMonnier](https://github.com/YMonnier)


License
-------
Sql-Help is available under the MIT license. See the [LICENSE](https://github.com/YMonnier/sql-helper/blob/master/LICENSE) file for more info.
