# sql-helper

[![Java](https://img.shields.io/badge/Java-8-blue.svg?style=flat)](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)

JPA Annotation Helper

CRUD + findById methods generator depending on attributes.

```java

  /**
  * Extends annotation can be applied on a Class.
  * This Annotation allows to create the
  * CRUD operations for the specific Class.
  * @see Attr
  * @see com.ymonnier.sql.help.service.CrudService
  * @see com.ymonnier.sql.help.service.CrudServiceBean
  */
  @Target(value = ElementType.TYPE)
  @Retention(value = RetentionPolicy.SOURCE)
  public @interface Extends { }


  /**
   * Attr annotation can be applied on a Field Element.
   * This Annotation allows to create the 'findByATTR' method.
   * @see Extends
   * @see com.ymonnier.sql.help.service.CrudService
   * @see com.ymonnier.sql.help.service.CrudServiceBean
   */
  @Target(value = ElementType.FIELD)
  @Retention(value = RetentionPolicy.SOURCE)
  public @interface Attr { }
```

```java
   public interface CrudService<T> {
       /**
        * Save an entity into the database.
        *
        * @param object Entity object.
        * @return The entity updated(@ID).
        */
       public T save(T object);

       /**
        * Update an entity into the database.
        *
        * @param object Entity object.
        * @return The entity updated.
        */
       public T update(T object);

       /**
        * Delete an entity into the database.
        *
        * @param object Entity object.
        */
       public void delete(T object);

       /**
        * Create a QueryBuilder for a named query.
        *
        * @param namedQueryName The string query name.
        * @return QueryBuilder object. @see QueryBuilder
        */
       public QueryBuilder<T> findWithNamedQuery(String namedQueryName);

       /**
        * Create a QueryBuilder for a specific query.
        *
        * @param query The string query (native SQL or JPQL).
        * @return QueryBuilder object. @see QueryBuilder
        */
       public QueryBuilder<T> findWithQuery(String query);

       /**
        * Close the EntityManager connection.
        */
       public void close();
   }
```

## Usage

Maven dependency:
```maven
  <dependency>
      <groupId>com.github.ymonnier.sql-helper</groupId>
      <artifactId>ym-sql-helper</artifactId>
      <version>LATEST</version>
  </dependency>
```
#### Your entity

```java
   @Entity
   @Extends
   @NamedQuery(name = "Person.all",
         query = "SELECT p from Person p")
   public class Person {
     @Attr
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Attr
     private String name;

     @Attr
     private String address;

     private String test;

     // getters, setters, builder, ...
   }
```

#### Will generate

```java
public class _Person {

  public _Person() {
      service = new CrudServiceBean<Person>();
  }

  public _Person(EntityManager em) {
      service = new CrudServiceBean<Person>(em);
  }

  public EntityManager getEntityManager() {
      return service.entityManager;
  }

  public Person save(Person object) {
      return service.save(object);
  }

  public Person update(Person object) {
      return service.update(object);
  }

  public void delete(Person object) {
      service.delete(object);
  }

  public QueryBuilder<Person> findById(java.lang.Long id) {
      return find("id", id);
  }

  public QueryBuilder<Person> findByName(java.lang.String name) {
      return find("name", name);
  }

  // ...

  public QueryBuilder<Person> findWithNamedQuery(String namedQueryName) {
      return service.findWithNamedQuery(namedQueryName);
  }

  public QueryBuilder<Person> findWithQuery(String query) {
      return service.findWithQuery(query);
  }
}
```


#### Main

Before to use ORM operations, initialize the `EntityManagerFactory`:

* `EntityManagerFactory.init("persistence-unit name");` (from your `persistence.xml`)

```java
  public class App {
    public static void main(String[] args) {
        EntityManagerFactory.init("TestPersistence");

        _Person service = new _Person();

        Person person = new new Person.Builder()
                .setName("John")
                .setAddress("10 Peace Street.")
                .setTest("My super test")
                .build();

        // Save object
        EntityTransaction transac = service.getEntityManager().getTransaction();
        transac.begin();
        person = service.save(entity);
        transac.commit();

        // Get all
        List<Person> persons = service
                .findWithNamedQuery("Person.all")
                .list()

        // Get specific person
        Person p = service
                .findById(2)
                .first();
    }
}
```

Contributor
------------

* [@YMonnier](https://github.com/YMonnier)


License
-------
Sql-Help is available under the MIT license. See the [LICENSE](https://github.com/YMonnier/sql-helper/blob/master/LICENSE) file for more info.
