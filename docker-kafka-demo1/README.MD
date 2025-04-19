# Kafka demo 1

```sh
docker build -f kafkaServer.Dockerfile -t demo1 . && docker run --rm -it demo1

bash mkdirs.sh && docker-compose -f ./demo1.docker-compose.yaml -p demo1 up --build

docker-compose -p demo1 down
docker-compose -p demo1 down && sudo rm -rf ./_data

kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic connect-test
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic connect-test
kafka-topics.sh --bootstrap-server localhost:9092 --list
kafka-topics.sh --bootstrap-server kafka_server:9092 --list

docker network inspect kafka-net

ping kafka_server

docker run --rm -it kafka_connect

curl -X POST http://localhost:8083/connectors \
  -H "Content-Type: application/json" \
  -d @postgres-jdbc-source.json

psql -h postgres -p 5432 -U postgres -d demo_db -W
```
