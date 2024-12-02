# Spring JPA Demo

Sandbox for JPA experiments/POCs

## Prerequisites and setup

1. jdk-21, docker, docker-compose
2. Run `docker-compose -f ./docker/mysql-docker-compose.yaml  up -d`
3. `mvnw -f ./spring-jpa-demo-parent/pom.xml clean install -DskipTests`
4. To test a module like `demo1-liquibase`
   1. run manually `src/main/scripts/mysqlusers.sql`
   2. run tests from that module

## Setup IDE

1. For **IntelliJ** add **Adapter for Eclipse Code Formatter**
2. Add eclipse formatter config
3. Add import order file

## MySQL

- Run `docker-compose -f ./mysql-docker-compose.yaml  up -d` in docker folder
- Connect `mysql -h localhost -P 3306 --protocol=tcp -u root -p`
- Connect `mysql -h localhost -P 3306 --protocol=tcp -u root -p'qwerty'`

## Maven

- Generate parent/child module: `mvn -f ./pom.xml archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DgroupId=com.ipostu.service.module -DartifactId=service-module`

## Summary

### Demo 1

### Demo 2

### Demo 3

### Demo 4

### Demo 5

### Demo 6

### Demo 7

### Demo 8

### Demo 9

### Demo 10

### Demo 11

### Demo 12

### Demo 13 - Credit card encryption at rest

- ***Use case: on write(insert/update) data should be encrypted, on read(select) decrypted***
  - **Hibernate Interceptor** - write works, read has a bug for `EmptyInterceptor.onLoad`, the state arg is always null. Details <https://discourse.hibernate.org/t/hibernate-v6-preloadevent-getstate-always-is-null-issue/7342>
  - **Hibernate Listener** - works, test is also working, precondition is to turn off the `InterceptorRegistration` config bean
  - **Hibernate Callback** - works, test is also working, precondition is to turn off the `InterceptorRegistration,ListenerRegistration` config beans
  