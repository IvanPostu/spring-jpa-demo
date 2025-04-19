FROM ubuntu:20.04

ENV CONNECT_BOOTSTRAP_SERVERS=${CONNECT_BOOTSTRAP_SERVERS:-localhost:90921}

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

ENV APP_HOME=/home/app_user
ENV KAFKA_DATA=$APP_HOME/_kafka_data
ENV KAFKA_HOME=$APP_HOME/kafka_2.13-4.0.0
ENV PATH=$KAFKA_HOME:$PATH
ENV PATH=$KAFKA_HOME/bin:$PATH

RUN mv $KAFKA_HOME/config/connect-standalone.properties $KAFKA_HOME/config/connect-standalone.properties.bak
# original config
RUN cat <<EOF > $KAFKA_HOME/config/connect-standalone.properties
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# These are defaults. This file just demonstrates how to override some settings.
bootstrap.servers=localhost:9092

# The converters specify the format of data in Kafka and how to translate it into Connect data. Every Connect user will
# need to configure these based on the format they want their data in when loaded from or stored into Kafka
key.converter=org.apache.kafka.connect.json.JsonConverter
value.converter=org.apache.kafka.connect.json.JsonConverter
# Converter-specific settings can be passed in by prefixing the Converter's setting with the converter we want to apply
# it to
key.converter.schemas.enable=true
value.converter.schemas.enable=true

offset.storage.file.filename=/tmp/connect.offsets
# Flush much faster than normal, which is useful for testing/debugging
offset.flush.interval.ms=10000

# Set to a list of filesystem paths separated by commas (,) to enable class loading isolation for plugins
# (connectors, converters, transformations). The list should consist of top level directories that include 
# any combination of: 
# a) directories immediately containing jars with plugins and their dependencies
# b) uber-jars with plugins and their dependencies
# c) directories immediately containing the package directory structure of classes of plugins and their dependencies
# Note: symlinks will be followed to discover dependencies or plugins.
# Examples: 
# plugin.path=/usr/local/share/java,/usr/local/share/kafka/plugins,/opt/connectors,
#plugin.path=
EOF
RUN cat $KAFKA_HOME/config/connect-standalone.properties | diff - $KAFKA_HOME/config/connect-standalone.properties.bak

# https://kafka.apache.org/quickstart
RUN echo "plugin.path=$KAFKA_HOME/libs/connect-file-4.0.0.jar" >> $KAFKA_HOME/config/connect-standalone.properties
RUN cat <<EOF > $KAFKA_HOME/config/connect-file-source.properties
name=local-file-source
connector.class=FileStreamSource
tasks.max=1
file=${APP_HOME}/test.txt
topic=connect-test
EOF
RUN cat <<EOF > $KAFKA_HOME/config/connect-file-sink.properties
name=local-file-sink
connector.class=FileStreamSink
tasks.max=1
file=${APP_HOME}/test.sink.txt
EOF

RUN cat <<EOF > setup.sh
#!/bin/bash
set -eu

sed -i \
    "s@bootstrap.servers=localhost:9092@bootstrap.servers=\$CONNECT_BOOTSTRAP_SERVERS@g" \
    $KAFKA_HOME/config/connect-standalone.properties

EOF

# dummy data
RUN echo -e "foo\nbar" > $APP_HOME/test.txt

ENTRYPOINT ["/bin/bash", "-c", "/bin/bash setup.sh && $KAFKA_HOME/bin/connect-standalone.sh $KAFKA_HOME/config/connect-standalone.properties $KAFKA_HOME/config/connect-file-source.properties $KAFKA_HOME/config/connect-file-sink.properties"]
# ENTRYPOINT ["/bin/bash", "-c", "/bin/bash setup.sh && sleep 10h"]
# ENTRYPOINT ["/bin/bash", "-c", "sleep 10h"]
