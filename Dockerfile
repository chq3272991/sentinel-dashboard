FROM amd64/buildpack-deps:buster-curl as installer

ARG SENTINEL_VERSION=1.8.7
FROM openjdk:8-jre-slim

# copy sentinel jar
COPY ./target/nacos-sentinel-dashboard.jar /home/nacos-sentinel-dashboard.jar

ENV JAVA_OPTS '-Dserver.port=8858 -Dcsp.sentinel.dashboard.server=localhost:8858'

RUN chmod -R +x /home/nacos-sentinel-dashboard.jar

EXPOSE 8858

CMD java ${JAVA_OPTS} -jar /home/nacos-sentinel-dashboard.jar

