# Spring JPA Demo

Sandbox for JPA POCs

## References

- Maven docker plugin - <https://github.com/fabric8io/docker-maven-plugin>
- Logback config - <https://javatechonline.com/logback-xml-configuration-examples/>
- Prepare tomcat for production - <https://contabo.com/blog/apache-tomcat-complete-guide-to-setup-and-configuration/>
- Regex Cheatsheet <https://www.rexegg.com/regex-quickstart.php>
- JPA Query Methods <https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html>

## Prerequisites and setup

1. jdk-21, docker, docker-compose
2. Run `docker-compose -f ./docker/dbs-docker-compose.yaml  up -d`
3. `mvnw -f ./spring-jpa-demo-parent/pom.xml clean install -DskipTests`
4. To test a module like `demo1-liquibase`
   1. run manually `src/main/scripts/mysqlusers.sql`
   2. run tests from that module

## Connect

```sh
docker exec -it postgres_demo bash
docker exec -it mysql_demo bash

mysql -h localhost -P 3306 --protocol=tcp -u root -p'qwerty' -D demo_db
psql -h localhost -p 5432 -U postgres -d demo_db

# -W - to input the password
psql -h 192.168.0.97 -p 5432 -U superadmin -d postgres -W
```

## Setup IDE

1. For **IntelliJ** add **Adapter for Eclipse Code Formatter**
2. Add eclipse formatter config
3. Add import order file

## Maven

```sh
# generate child module
# for webapp use: org.apache.maven.archetypes:maven-archetype-webapp
mvnw -f ./pom.xml archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DgroupId=com.ipostu.service.module -DartifactId=service-module

mvnw -f ./spring-jpa-demo-parent/pom.xml clean install -DskipTests

mvnw -f ./spring-jpa-demo-parent/demo16-springboot-docker-layers/pom.xml clean package -DskipTests

# dependency tree
projectName=demo-spring4-war-annotation && mvnw -f ./$projectName/pom.xml dependency:tree
```

## VM Tomcat deploy (dev env)

```sh
# on VM
nix-shell -p jdk17
nix-shell -p postgresql_16

# host machine
projectName=demo-spring11-jar-boot \
  && mvnw -f ./$projectName/pom.xml clean package \
  && mkdir -p ~/VMSharedD12/_depl \
  && cp -r ./$projectName/target/$projectName ~/VMSharedD12/_depl/$projectName \
  && touch ~/VMSharedD12/_depl/signal1.txt

#############################################
# bash script file pool-artifact.sh
#!/bin/bash

export CATALINA_HOME=/home/test/apache-tomcat-10.1.35
SIGNAL_FILE=~/Desktop/sharedWithHost/_depl/signal1.txt

deploy_war_files() {
  echo "Running: catalina.sh stop"
  $CATALINA_HOME/bin/catalina.sh stop

  echo "Cleaning: $CATALINA_HOME/webapps/*"
  rm -rf $CATALINA_HOME/webapps/*

  echo "Copying: ~/Desktop/sharedWithHost/_depl to $CATALINA_HOME/webapps"
  cp -r ~/Desktop/sharedWithHost/_depl/* $CATALINA_HOME/webapps

  echo "Running: catalina.sh start"
  $CATALINA_HOME/bin/catalina.sh start
}

while true; do
  if [ -f $SIGNAL_FILE ]; then
    rm $SIGNAL_FILE
    deploy_war_files
    rm -rf ~/Desktop/sharedWithHost/_depl
  fi

  sleep 0.5
done
```

## Observations

- Maven surefire plugin doesn't see non-public test classes as well as non-public test class methods
- logback.xml
  - if `${catalina.home}` is undefined, it will create `catalina.home_IS_UNDEFINED` in the path where the program is run
- CRUD
  - GET /posts
  - POST /posts
  - GET /posts/new
  - GET /posts/:id/edit
  - GET /posts/:id
  - PATCH /posts/:id
  - DELETE /posts/:id
- REST (representational state transfer)
  - GET /posts
  - GET /posts/:id
  - POST /posts
  - PATCH /posts/:id
  - DELETE /posts/:id
- The `<form />` in html only supports: GET and POST, but in order to do all kind of requests spring uses `HiddenHttpMethodFilter`
- Configure postgres on VM with Debian 12 with nix-shell
  - `nix-shell -p postgresql_16`

  - `initdb -D ./pgdata`
  - `sudo chown test /run/postgresql`
  - `sudo chown <user> /run/postgresql`
  - `pg_ctl -D ./pgdata -l logfile start`
  - `psql -d postgres` connect via postgres user
  - `CREATE ROLE superadmin WITH SUPERUSER CREATEDB CREATEROLE LOGIN PASSWORD 'qwerty';`
  - `psql -U superadmin -d postgres -W`
    - `pg_hba.conf`

    ```sh
      # IPv4 local connections
      host all all 0.0.0.0/0 scram-sha-256
      # IPv6 local connections
      host all all ::/0 scram-sha-256
    ```

  - `pgdata/postgresql.conf` -> `password_encryption = scram-sha-256`
  - Enable remote TCP connections, edit `pgdata/postgresql.conf` - from `listen_addresses='localhost'` to `listen_addresses='*'`
  - `pg_ctl -D ./pgdata -l logfile restart`
  - restart postgres process
    - `sudo pkill -e postgre`
    - `sudo chown -R test: /run/postgresql`
    - `pg_ctl -D ./pgdata -l logfile restart`

- Virtualbox VM
  - Networking: NAT -> Bridged Adapter - expose VM to network
  - Run on guest OS `ip addr` and use this ip in host OS
- `DriverManagerDataSource` spring's implementation of `DataSource` which creates a new connection when `getConnection` is called
- `jdbcTemplate.batchUpdate` 1000x more optimized that inserting/updating records in loop
- In postres (and any other RDMS) `SELECT * FROM ...` without explicit order return records by order of insertion BUT `UPDATE` query breaks this order, so the only one guarantee of order is with explicit `ORDER BY`
- There is a way to define custom `jakarta.validation` annotations with custom logic, the impl. is used automatically, client code should only use annotation
  - To use spring beans, it is necessary to use a different approach, a custom bean which implements `org.springframework.validation.Validator`, the bean should be used manually (no annotations)
- Thymeleaf template engine by default doesn't support `UTF-8`, it is needed to run `new ThymeleafViewResolver().setCharacterEncoding("UTF-8")`
- To use hibernate, add jdbc driver, hibernate-core and `hibernate.properties`
- Hibernate usage: create hibernate `Configuration` instance (internally reads `hibernate.properties`), create `sessionFactory` (expensive op.), create session, `session.beginTransaction(); op. session.getTransaction().commit();`
- Hibernate caches items inside the transaction
- It is required in case of OneToMany to set item on both sides in order to avoid cache inconsistencies, e.g. Person -> List<Items>, if a new item is created, `person.getItems().add(item); item.setPerson(person);`
- Hibernate life-cycles:
  - Transient - entity type object created by client code, becomes Persistent after `session.save(entityObject)`
  - Persistent (Managed) - `session.get` is Persistent, in other words if client code calls setters, then hibernate will notice it and will execute related sql on `session.getTransaction().commit()`
  - Detached - `session.detach` or when `session` is closed, can be linked to Persistent Context through `session.merge(...)`
  - Removed - entity object after `session.remove()`
- Owning side (entity) is an entity which doesn't have @One|ManyToOne|Many(mapedBy="..."), in other words it is the opposite entity
- `fetch = FetchType.LAZY|EAGER`
  - OneToMany and ManyToMany by default are Lazy
  - OneToOne and ManyToOne by default is Eager
- `Hibernate.initialize(person.getItems())` is used to load lazy entities, after this call we can use `person.getItems()` in detached `person`
- Persistence context = Level 1 cache
- Hibernate core(main hibernate dependency) by default provides and implement `javax.persistence.*` renamed to `jakarta.persistence.*` which is JPA specification
- N+1 problem

  ```java
  // 1 Query
  List<Person> people = session.createQuery("SELECT p FROM Person p", Person.class);

  // N Queries
  for(Person person : people) {
    LOG.info("Person {} has: {}", person.getName(), person.getItems());
  }

  // Result:
  // 1 time:  SELECT * FROM person;
  // N times: SELECT * FROM items WHERE person_id=?
  ```

  - `FetchType.EAGER` do not help!
  - Solution 1 - HQL with LEFT JOIN FETCH

    ```java
    List<Person> people = session.createQuery("SELECT p FROM Person p LEFT JOIN FETCH p.items", Person.class);
    ```

- Difference between `session.get` and `session.load`
  - `session.get` does `SELECT ...` on call
  - `session.load` returns a proxy object with only one populated field, `id`, if client code calls any `get` method on the column field, then the proxy will do `SELECT ...`
  - usage of `session.load`

  ```java
  Item item = new Item("...");
  Person personProxy = session.load(id, Person.class);
  item.setOwner(personProxy);
  session.save(item);
  ```

- Spring Data JPA exposes an API for paging and sorting, e.g.:

  ```java
  peopleRepository.findAll(PageRequest.of(0, 20,
          Sort.by(List.of(new Sort.Order(Sort.Direction.DESC, "name"),
                  new Sort.Order(Sort.Direction.DESC, "age")))));
  ```

- `@jakarta.persistence.Transient` tells hibernate to ignore this field
- Build and run spring-boot app:
  - `projectName=demo-spring11-jar-boot   && mvnw -f ./$projectName/pom.xml clean package && java -jar ./demo-spring11-jar-boot/target/demo-spring11-jar-boot-1.0-SNAPSHOT.jar`
  - `projectName=demo-spring11-jar-boot   && mvnw -f ./$projectName/pom.xml  spring-boot:run`
- Spring Security - general
  - client code should implement `AuthenticationProvider`
    - it takes as an input `Authentication+Credentials`
    - returns as an output `Authentication+Principal`
    - `Cookie/Sessions == Map<String, Object>`
    - After successful login, `Principal` is stored in session and linked to cookie which is returned to user
    - Spring security provides a filter which gets by cookie principal object and keeps it in thread local for the current request
- Spring Boot Security Starter
  - By default all mappings are secured, default username is `user`, the password is shown in cli: `Using generated security password: d7d4e2e1-7caf-467c-a54b-4fe48ffc30c9`
- Spring Security

  ```java
  @Component
  public class AuthProviderImpl implements AuthenticationProvider
  ```

  - `AuthenticationProvider` isn't required, spring provides its own one, the client code should only define new one if an additional logic is needed
- CSRF - Cross Site Request Forgery
  - Protection for endpoints that change data, such as PUT/POST/DELETE
  - Backend generates one time token which is injected into the html and is hidden for user
  - On request this token is also added into the body of request and backend verifies it
- Authorization in pring security
  - Authority - allowed action, e.g. `BAN_USER`, `WITHDRAW_MONEY`, `SEND_MONEY`
  - Role `ADMIN`, `USER`
- `.hasRole("ADMIN")` is exactly same as `.hasAuthority("ROLE_ADMIN")`
- `@Controller` can return json response only if method is annotated with `@ResponseBody`, by default it tries to resolve view
  - `@RestController` doesn't require `@ResponseBody`, it returns JSON by default

## TODO

- Bean pre|post processor
- AOP xml|java
- Sql stored procedure with transaction & CallableStatement
- Spring web + JaxRS (Jersey)
- grpc
- JPA: column based on `Enum` ordinal|string|custom value (Converter usage)

## Summary

### Spring mvc demo1

#### Demo webapp 1

- Embedded tomcat
- Accessible through: `http://localhost:8080/springapp/app`
- Context initialization via implementing `WebApplicationInitializer`

### spring-jpa-demo-parent - demo1-liquibase

- Liquibase + Spring Boot
- Liquibase is triggered on spring boot startup

### spring-jpa-demo-parent - demo2-flyway

- Flyway (default config) in combination with spring boot

### spring-jpa-demo-parent - demo3-ids

- Different kind of keys (autoincrement, UUID string, UUIDD binary)
- natural vs. surrogate key
- composite keys

### spring-jpa-demo-parent - demo4-jdbc

JDBC usage + flyway config

### spring-jpa-demo-parent - demo5-jdbc-template

JDBC Template

### spring-jpa-demo-parent - demo6-hibernate-dao

JPA EntityManagerFactory/EntityManager and TypedQuery usage

### spring-jpa-demo-parent - demo7-hibernate-queries

JPA EntityManagerFactory/EntityManager and TypedQuery usage

\+ @NamedQuery (query on entity class annotation)

### spring-jpa-demo-parent - demo8-jpa-query

JPA

### spring-jpa-demo-parent - demo9-pagination

Pagination (Imperative JPA (EntityManager & TypedQuery), JDBC Template, JpaRepository)

### spring-jpa-demo-parent - demo10-hibernate_mappings

- Hibernate mappings OneToMany, ManyToMany, @Embedded/@Embeddable, @Version for optimistic lock
- Before main run `testDataLoader`
- N+1 - `@Fetch(FetchMode.SUBSELECT)` does left join

  ```java
    @OneToMany(mappedBy = "orderHeader", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<OrderLine> orderLines;
  ```

### spring-jpa-demo-parent - demo11-inheritance

- q

### spring-jpa-demo-parent - demo12-legacy-db-mapping

### spring-jpa-demo-parent - demo13-credit-card-encryption

- ***Use case: on write(insert/update) data should be encrypted, on read(select) decrypted***
  - **Hibernate Interceptor** - write works, read has a bug for `EmptyInterceptor.onLoad`, the state arg is always null. Details <https://discourse.hibernate.org/t/hibernate-v6-preloadevent-getstate-always-is-null-issue/7342>
    - One insert and one update as one transaction
  - **Hibernate Listener** - works, test is also working, precondition is to turn off the `InterceptorRegistration` config bean
    - One insert and one update as one transaction
  - **JPA Callback** - works, test is also working, precondition is to turn off the `InterceptorRegistration,ListenerRegistration` config beans
    - One insert
  - **JPA Converter** - works, test is also working, best option!
    - One insert

### spring-jpa-demo-parent - demo14-multipledatasources

### spring-jpa-demo-parent - demo15-springdatarest

#### Usage

```sh
GET http://localhost:8080/api/v1/beer

GET http://localhost:8080/api/v1/beer/search/findAllByBeerStyle?beerStyle=ALE

GET http://localhost:8080/api/v1/beer/a5d1917a-de91-4172-aac8-a3399883d2b2

# Create
POST http://localhost:8080/api/v1/beer

{
  "beerName": "Mango Bobs - 990",
  "beerStyle": "ALE",
  "upc": "0631234200036",
  "quantityOnHand": 4344,
  "price": 10.26
}

GET http://localhost:8080/api/v1/beer/578b5f18-0a64-4a6c-a056-8d0d22e9d8ab

# Update
PUT http://localhost:8080/api/v1/beer/578b5f18-0a64-4a6c-a056-8d0d22e9d8ab

{
  "beerName": "Mango Bobs - AAA",
  "beerStyle": "ALE",
  "upc": "0631234200036",
  "quantityOnHand": 4344,
  "price": 10.26
}

# Delete
DELETE http://localhost:8080/api/v1/beer/578b5f18-0a64-4a6c-a056-8d0d22e9d8ab
```

### Demo 16 - SpringBoot docker layers

Simple image

```sh
FROM openjdk:21

ENV JAVA_OPTS " -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

WORKDIR application

COPY ./spring-jpa-demo-parent/demo16-springboot-docker-layers/target/demo16-springboot-docker-layers-1.0-SNAPSHOT.jar ./

ENTRYPOINT ["java", "-jar", "demo16-springboot-docker-layers-1.0-SNAPSHOT.jar"]
```

Layered spring boot image

```sh
FROM openjdk:21 AS builder
WORKDIR application
ADD ./spring-jpa-demo-parent/demo16-springboot-docker-layers/target/demo16-springboot-docker-layers-1.0-SNAPSHOT.jar ./
RUN java -Djarmode=layertools -jar demo16-springboot-docker-layers-1.0-SNAPSHOT.jar extract

FROM openjdk:21

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]
```

Usage

```sh
# build regular image with fat jar
docker build  -f ./spring-jpa-demo-parent/demo16-springboot-docker-layers/src/main/dockerBase/Dockerfile -t demo16 .

# run container
docker run demo16

# build layered spring boot application
docker build  -f ./spring-jpa-demo-parent/demo16-springboot-docker-layers/src/main/docker/Dockerfile -t demo16 .

# run container
docker run demo16
```

### Other 1

JDBC Template with transaction handling

#### To use xml base config

```java
public interface AccountService {

    //    @Transactional
    void updateAccountFunds();

    //    @Transactional(propagation = Propagation.NESTED)
    void addFundsToAccount(int amount);
}

@SpringBootApplication
@ImportResource("classpath:application-context.xml")
public class SpringTransactionDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringTransactionDemo.class, args);
    }

}
```

#### To use annotation driven config

```java
public interface AccountService {

       @Transactional
    void updateAccountFunds();

       @Transactional(propagation = Propagation.NESTED)
    void addFundsToAccount(int amount);
}

@SpringBootApplication
//@ImportResource("classpath:application-context.xml")
public class SpringTransactionDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringTransactionDemo.class, args);
    }

}
```

### Other 2 Spring Security POC

- <img src="./_forReadme/img1.png" style="width: 500px" />
- <img src="./_forReadme/img2.png" style="width: 500px" />
