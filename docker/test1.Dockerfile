FROM ubuntu:20.04

# https://www.docker.com/blog/understanding-the-docker-user-instruction/
RUN useradd -ms /bin/bash app_user
USER app_user

WORKDIR /home/app_user

RUN mkdir -p app

ENTRYPOINT ["/bin/bash", "-c", "echo \"abc123456\" > /home/app_user/app/test.txt && cat /home/app_user/app/test.txt"]
# docker build -f test1.Dockerfile -t test1 . && docker run --rm -it --user $(id -u):$(id -g) --mount type=bind,source="$(pwd)"/target,target=/home/app_user/app test1
# docker build -f test1.Dockerfile -t test1 . && docker run --rm -v mydata:/home/app_user/app -it test1

# docker volume create mydata

 
# docker volume create \
#   --driver local \
#   --opt type=none \
#   --opt device=/home/iv127/Projects/spring-jpa-demo/docker/mydata \
#   --opt o=bind \
#   mydata

# docker-compose -f ./test1-docker-compose.yaml -p test1 up --build
# docker-compose -p test1 down
