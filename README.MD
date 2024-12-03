# Spring JPA Demo

Sandbox for JPA experiments/POCs

## References

- Maven docker plugin - <https://github.com/fabric8io/docker-maven-plugin>

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
