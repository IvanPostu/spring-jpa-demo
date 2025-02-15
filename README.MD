# Spring JPA Demo

Sandbox for JPA POCs

## References

- Maven docker plugin - <https://github.com/fabric8io/docker-maven-plugin>
- Logback config - <https://javatechonline.com/logback-xml-configuration-examples/>
- Prepare tomcat for production - <https://contabo.com/blog/apache-tomcat-complete-guide-to-setup-and-configuration/>

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
projectName=demo-spring6-war-postgres \
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

- Virtualbox VM
  - Networking: NAT -> Bridged Adapter - expose VM to network
  - Run on guest OS `ip addr` and use this ip in host OS
- `DriverManagerDataSource` spring's implementation of `DataSource` which creates a new connection when `getConnection` is called
password_encryption = scram-sha-256

## Summary

### Spring mvc demo1

#### Demo webapp 1

- Embedded tomcat
- Accessible through: `http://localhost:8080/springapp/app`
- Context initialization via implementing `WebApplicationInitializer`

### Demo 1

Usage of liquibase which is triggered on spring boot startup

```xml
<!-- pom.xml -->
<!-- is inherited from spring-boot-starter-parent -->
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>

<!-- ... -->

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <!--            not implemented yet-->
        <plugin>
            <groupId>org.liquibase</groupId>
            <artifactId>liquibase-maven-plugin</artifactId>
            <version>4.2.2</version>
            <configuration>
                <url>jdbc:mysql://127.0.0.1:3306/bookdb?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC</url>
                <username>bookadmin</username>
                <password>password</password>
                <outputChangeLogFile>changelog.xml</outputChangeLogFile>
                <changeSetAuthor>TestUser</changeSetAuthor>
                <changelogSchemaName>bookdb</changelogSchemaName>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>${mysql.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

```properties
# ...
spring.jpa.hibernate.ddl-auto=validate
# default false, If you want to use data.sql to populate a schema created by Hibernate, set spring.jpa.defer-datasource-initialization to true
spring.jpa.defer-datasource-initialization=false

spring.liquibase.change-log=db/changelog/changelog-master.xml
```

### Demo 2

Flyway (default config) in combination with spring boot

### Demo 3

- Different kind of keys (autoincrement, UUID string, UUIDD binary)
- natural vs. surrogate key
- composite keys

### Demo 4

JDBC usage + flyway config

### Demo 5

JDBC Template

### Demo 6

JPA EntityManagerFactory/EntityManager and TypedQuery usage

### Demo 7

JPA EntityManagerFactory/EntityManager and TypedQuery usage

\+ @NamedQuery (query on entity class annotation)

### Demo 8

JPA

### Demo 9

Pagination (Imperative JPA (EntityManager & TypedQuery), JDBC Template, JpaRepository)

### Demo 10

- Hibernate mappings OneToMany, ManyToMany, @Embedded/@Embeddable, @Version for optimistic lock
- Before main run `testDataLoader`
- N+1 - `@Fetch(FetchMode.SUBSELECT)` does left join

  ```java
    @OneToMany(mappedBy = "orderHeader", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<OrderLine> orderLines;
  ```

### Demo 11

### Demo 12

### Demo 13 - Credit card encryption at rest

- ***Use case: on write(insert/update) data should be encrypted, on read(select) decrypted***
  - **Hibernate Interceptor** - write works, read has a bug for `EmptyInterceptor.onLoad`, the state arg is always null. Details <https://discourse.hibernate.org/t/hibernate-v6-preloadevent-getstate-always-is-null-issue/7342>
    - One insert and one update as one transaction
  - **Hibernate Listener** - works, test is also working, precondition is to turn off the `InterceptorRegistration` config bean
    - One insert and one update as one transaction
  - **JPA Callback** - works, test is also working, precondition is to turn off the `InterceptorRegistration,ListenerRegistration` config beans
    - One insert
  - **JPA Converter** - works, test is also working, best option!
    - One insert

### Demo 14 - Multiple Data Sources

### Demo 15 - Spring Data REST

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
