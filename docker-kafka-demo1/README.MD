# Kafka demo 1

## References

- <https://docs.confluent.io/kafka-connectors/jdbc/current/source-connector/source_config_options.html>
- <https://gist.github.com/yuwtennis/382dc317b69dec9866efed88251f99cb>

```sh
docker build -f kafkaServer.Dockerfile -t demo1 . && docker run --rm -it demo1

bash mkdirs.sh && docker-compose -f ./demo1.docker-compose.yaml -p demo1 up --build
docker-compose -p demo1 down && sudo rm -rf ./_data

docker-compose -p demo1 down

kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic connect-test
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic connect-test
kafka-topics.sh --bootstrap-server localhost:9092 --list
kafka-topics.sh --bootstrap-server kafka_server:9092 --list
kafka-console-consumer.sh --bootstrap-server kafka_server:9092 --from-beginning  --property print.key=true --property print.timestamp=true --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer --topic test-postgresql-jdbc-sample

docker exec -it kafka_connect bash
docker exec -u root -it kafka_connect bash

docker network inspect kafka-net

ping kafka_server

docker run --rm -it kafka_connect

curl -X POST http://localhost:8083/connectors \
  -H "Content-Type: application/json" \
  -d @postgres-jdbc-source.json

# https://docs.confluent.io/kafka-connectors/jdbc/current/source-connector/source_config_options.html
curl --location --request POST 'http://localhost:8083/connectors/' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name":"jdbc-source",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
    "tasks.max": 1,
    "topic.prefix": "test-postgresql-jdbc-",
    "connection.url": "jdbc:postgresql://postgres/demo_db?ssl=false",
    "connection.user": "postgres",
    "connection.password": "postgres",
    "mode": "incrementing",
    "incrementing.column.name": "id"
  }
}'


curl -X GET http://localhost:8083/connectors

curl --location --request GET 'http://localhost:8083/connector-plugins'

psql -h postgres -p 5432 -U postgres -d demo_db -W

# https://www.confluent.io/hub/
```
