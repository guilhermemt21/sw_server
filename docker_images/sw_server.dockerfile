FROM ubuntu:18.04

#Install Open JDK 11
RUN apt-get update \
    && apt-get -y install openjdk-11-jdk \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the main application.
COPY . /opt/sw_server/
WORKDIR /opt/sw_server/

RUN mvn clean install -DskipTests
RUN chmod +x /opt/sw_server/wait-for-it.sh

#ADD config.yml /opt/sw_server/config.yml