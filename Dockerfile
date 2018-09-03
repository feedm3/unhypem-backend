FROM openjdk:8
MAINTAINER Fabian Dietenberger piano.fabian@t-online.de

EXPOSE 8080
EXPOSE 5432

WORKDIR /srv/unhypem/
COPY build/libs/*.jar /srv/unhypem/

# CMD java -Dserver.port=$PORT -Dspring.profiles.active=prod  $JAVA_OPTS  -Xms64m -Xmx512m -Xss512k -XX:+UseConcMarkSweepGC  -jar /srv/unhypem/*.jar
CMD java -Dserver.port=8080 -Dspring.profiles.active=prod -Xms64m -Xmx512m -Xss512k -XX:+UseConcMarkSweepGC  -jar /srv/unhypem/*.jar