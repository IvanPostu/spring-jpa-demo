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
