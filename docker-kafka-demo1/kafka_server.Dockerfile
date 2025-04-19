FROM ubuntu:20.04

ENV KAFKA_CFG_ADVERTISED_LISTENERS=${KAFKA_CFG_ADVERTISED_LISTENERS:-PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093}

RUN apt-get update
RUN apt-get install -y wget \
    iputils-ping \
    git \
    gpg \
    bash

RUN DEBIAN_FRONTEND=noninteractive apt-get install -y openjdk-17-jdk    

RUN apt-get clean

# Set JAVA_HOME environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# create a simple user
RUN useradd app_user -m
WORKDIR /home/app_user

USER app_user

RUN wget https://dlcdn.apache.org/kafka/4.0.0/kafka_2.13-4.0.0.tgz

# https://dlcdn.apache.org/kafka/4.0.0/kafka_2.13-4.0.0.tgz.asc
RUN cat <<EOF > kafka_2.13-4.0.0.tgz.asc
-----BEGIN PGP SIGNATURE-----

iQIzBAABCAAdFiEEiAvZO/de3lU7apkYtMwQYbYXN/kFAmfT5tgACgkQtMwQYbYX
N/k7GQ//QMl/l6rY2OOVnXZ462wDzlmTvZM9qhiVCyDYarEXRhjy07RaHUbIRxvY
nyNxMv3Klm+A2EEYaDGPPcHZtCgsUXTOQ2gHZa9iXiizsahCt4GsO5cjBFyZRx2E
8JHFYoEOyXjwAZ3OD2LgHG0U4T1PZ0xfpbS/b93raglkvo1z97bPavZct0TXzqMr
jztNZq8n+8qxYzsEvwIcP96hxmcRJKGxvSvEETOYDszUAPfXlxAFZ3zUd7z2Sz4Y
csm9n9lhqdDgP6RK86nGh//pldK2wM46smnJKIYsEE4nUy29kOEc5dCwyv4uSKvP
FU40+eMI2B9KIWJfyaZgklYoD9KS8kNdACJ7uDSqrv0o76zm3qxL+bPQ0cuyhliW
Eu5lc11G9OGGggZ8xzLEs9majrYCDmrTDKScGAMoybxgaEP/XfDSXoZnuDRB0eTu
4K/yAOLy3J7Q7Y9TAoIhF99D35FQJEUUqgXqSUzxtDxlOAIm+e1nXe9m+dSAYJ7Y
DTK4q/TtOiKuiexISqkbDc+1rAjZe1nIp91jV7YDSyOvjZvy6hsKrFXeLYVPI8yk
Mf2EB7LQ2a4DeuaWSQmL+3sYMBotfiku/vDq4s6tzwYaL6KEg7nGKnKiaJO3Y3yc
j2rV+A+lLPMRAC4LW7LCoPUFDe2eaTiAA3REGUKXnGOhovynB5Y=
=mgln
-----END PGP SIGNATURE-----
EOF

RUN wget https://dlcdn.apache.org/kafka/KEYS -O - | gpg --import

# Verify signature
RUN gpg --verify kafka_2.13-4.0.0.tgz.asc kafka_2.13-4.0.0.tgz
RUN rm kafka_2.13-4.0.0.tgz.asc

RUN cat <<EOF > kafka_2.13-4.0.0.tgz.sha512
kafka_2.13-4.0.0.tgz: 00722AB0 A6B954E0 006994B8 D589DCD8 F26E1827 C47F70B6
                      E820FB45 AA35945C 19163B0F 188CAF0C AF976C11 F7AB005F
                      D368C54E 5851E899 D2DE687A 804A5EB9
EOF

# Verify hash of the file
RUN gpg --print-md SHA512 kafka_2.13-4.0.0.tgz | diff - kafka_2.13-4.0.0.tgz.sha512
RUN rm kafka_2.13-4.0.0.tgz.sha512

RUN tar -xzvf kafka_2.13-4.0.0.tgz
RUN rm kafka_2.13-4.0.0.tgz

RUN mkdir -p /home/app_user/_kafka_data/kafka

ENV KAFKA_DATA=/home/app_user/_kafka_data
ENV KAFKA_HOME=/home/app_user/kafka_2.13-4.0.0
ENV PATH=$KAFKA_HOME:$PATH
ENV PATH=$KAFKA_HOME/bin:$PATH

RUN mv $KAFKA_HOME/config/server.properties $KAFKA_HOME/config/server.properties.bak
# original config
RUN cat <<EOF > $KAFKA_HOME/config/server.properties
############################# Server Basics #############################

# The role of this server. Setting this puts us in KRaft mode
process.roles=broker,controller

# The node id associated with this instance's roles
node.id=1

# List of controller endpoints used connect to the controller cluster
controller.quorum.bootstrap.servers=localhost:9093

############################# Socket Server Settings #############################

# The address the socket server listens on.
# Combined nodes (i.e. those with `process.roles=broker,controller`) must list the controller listener here at a minimum.
# If the broker listener is not defined, the default listener will use a host name that is equal to the value of java.net.InetAddress.getCanonicalHostName(),
# with PLAINTEXT listener name, and port 9092.
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
listeners=PLAINTEXT://:9092,CONTROLLER://:9093

# Name of listener used for communication between brokers.
inter.broker.listener.name=PLAINTEXT

# Listener name, hostname and port the broker or the controller will advertise to clients.
# If not set, it uses the value for "listeners".
advertised.listeners=PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093

# A comma-separated list of the names of the listeners used by the controller.
# If no explicit mapping set in `listener.security.protocol.map`, default will be using PLAINTEXT protocol
# This is required if running in KRaft mode.
controller.listener.names=CONTROLLER

# Maps listener names to security protocols, the default is for them to be the same. See the config documentation for more details
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL

# The number of threads that the server uses for receiving requests from the network and sending responses to the network
num.network.threads=3

# The number of threads that the server uses for processing requests, which may include disk I/O
num.io.threads=8

# The send buffer (SO_SNDBUF) used by the socket server
socket.send.buffer.bytes=102400

# The receive buffer (SO_RCVBUF) used by the socket server
socket.receive.buffer.bytes=102400

# The maximum size of a request that the socket server will accept (protection against OOM)
socket.request.max.bytes=104857600


############################# Log Basics #############################

# A comma separated list of directories under which to store log files
log.dirs=/tmp/kraft-combined-logs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
num.partitions=1

# The number of threads per data directory to be used for log recovery at startup and flushing at shutdown.
# This value is recommended to be increased for installations with data dirs located in RAID array.
num.recovery.threads.per.data.dir=1

############################# Internal Topic Settings  #############################
# The replication factor for the group metadata internal topics "__consumer_offsets", "__share_group_state" and "__transaction_state"
# For anything other than development testing, a value greater than 1 is recommended to ensure availability such as 3.
offsets.topic.replication.factor=1
share.coordinator.state.topic.replication.factor=1
share.coordinator.state.topic.min.isr=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1

############################# Log Flush Policy #############################

# Messages are immediately written to the filesystem but by default we only fsync() to sync
# the OS cache lazily. The following configurations control the flush of data to disk.
# There are a few important trade-offs here:
#    1. Durability: Unflushed data may be lost if you are not using replication.
#    2. Latency: Very large flush intervals may lead to latency spikes when the flush does occur as there will be a lot of data to flush.
#    3. Throughput: The flush is generally the most expensive operation, and a small flush interval may lead to excessive seeks.
# The settings below allow one to configure the flush policy to flush data after a period of time or
# every N messages (or both). This can be done globally and overridden on a per-topic basis.

# The number of messages to accept before forcing a flush of data to disk
#log.flush.interval.messages=10000

# The maximum amount of time a message can sit in a log before we force a flush
#log.flush.interval.ms=1000

############################# Log Retention Policy #############################

# The following configurations control the disposal of log segments. The policy can
# be set to delete segments after a period of time, or after a given size has accumulated.
# A segment will be deleted whenever *either* of these criteria are met. Deletion always happens
# from the end of the log.

# The minimum age of a log file to be eligible for deletion due to age
log.retention.hours=168

# A size-based retention policy for logs. Segments are pruned from the log unless the remaining
# segments drop below log.retention.bytes. Functions independently of log.retention.hours.
#log.retention.bytes=1073741824

# The maximum size of a log segment file. When this size is reached a new log segment will be created.
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
log.retention.check.interval.ms=300000
EOF

# https://stackoverflow.com/a/27276110
RUN sed -i \
    "s@log.dirs=/tmp/kraft-combined-logs@log.dirs=/home/app_user/_kafka_data/kafka@g" \
    $KAFKA_HOME/config/server.properties

RUN cat <<EOF > setup.sh
#!/bin/bash
set -eu

FILE="$KAFKA_DATA/KAFKA_CLUSTER_ID.txt"

sed -i \
    "s@advertised.listeners=PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093@advertised.listeners=\$KAFKA_CFG_ADVERTISED_LISTENERS@g" \
    $KAFKA_HOME/config/server.properties

if [ ! -f "\$FILE" ]; then
    $KAFKA_HOME/bin/kafka-storage.sh random-uuid > "\$FILE"
    echo "File created: \$FILE, Content: \$(cat \$FILE)"
    KAFKA_CLUSTER_ID=\$(cat \$FILE)

    $KAFKA_HOME/bin/kafka-storage.sh format --standalone -t \$KAFKA_CLUSTER_ID -c $KAFKA_HOME/config/server.properties
else
    echo "File already exists: \$FILE, Content: \$(cat \$FILE)"
fi

EOF

ENTRYPOINT ["/bin/bash", "-c", "/bin/bash setup.sh && $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties"]

