FROM ubuntu:20.04

RUN apt-get update
RUN apt-get install -y wget \
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

RUN cat <<EOF > kafka_2.13-4.0.0.tgz.sha512
kafka_2.13-4.0.0.tgz: 00722AB0 A6B954E0 006994B8 D589DCD8 F26E1827 C47F70B6
                      E820FB45 AA35945C 19163B0F 188CAF0C AF976C11 F7AB005F
                      D368C54E 5851E899 D2DE687A 804A5EB9
EOF

# Verify hash of the file
RUN gpg --print-md SHA512 kafka_2.13-4.0.0.tgz | diff - kafka_2.13-4.0.0.tgz.sha512

RUN tar -xzvf kafka_2.13-4.0.0.tgz

WORKDIR /home/app_user/kafka_2.13-4.0.0

RUN cat <<EOF > setup.sh
#!/bin/bash

FILE="KAFKA_CLUSTER_ID.txt"

if [ ! -f "\$FILE" ]; then
    bin/kafka-storage.sh random-uuid > "\$FILE"
    echo "File created: \$FILE, Content: \$(cat \$FILE)"
    KAFKA_CLUSTER_ID=\$(cat \$FILE)

    bin/kafka-storage.sh format --standalone -t \$KAFKA_CLUSTER_ID -c config/server.properties
else
    echo "File already exists: \$FILE, Content: \$(cat \"\$FILE\")"
fi

EOF

ENTRYPOINT ["/bin/bash", "-c", "/bin/bash setup.sh && bin/kafka-server-start.sh config/server.properties"]

