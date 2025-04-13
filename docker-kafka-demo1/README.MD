# Kafka demo 1

```sh
docker build -f kafkaServer.Dockerfile -t demo1 . && docker run --rm -it demo1

docker-compose -f ./demo1.docker-compose.yaml -p demo1 up --build
```
