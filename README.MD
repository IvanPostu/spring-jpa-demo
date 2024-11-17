# Spring JPA Demo

Sandbox for JPA experiments

## Prerequisites

1. For **IntelliJ** add **Adapter for Eclipse Code Formatter**
2. Add eclipse formatter config
3. Add import order file

## MySQL

- Run `docker-compose -f ./mysql-docker-compose.yaml  up -d` in docker folder
- Connect `mysql -h localhost -P 3306 --protocol=tcp -u root -p`
- Connect `mysql -h localhost -P 3306 --protocol=tcp -u root -p'qwerty'`
