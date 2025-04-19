# Kafka demo 1

```sh
docker build -f kafkaServer.Dockerfile -t demo1 . && docker run --rm -it demo1

bash mkdirs.sh && docker-compose -f ./demo1.docker-compose.yaml -p demo1 up --build

docker-compose -p demo1 down

kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic connect-test
kafka-topics.sh --bootstrap-server localhost:9092 --list
kafka-topics.sh --bootstrap-server kafka_server:9092 --list

docker network inspect kafka-net

ping kafka_server
```
