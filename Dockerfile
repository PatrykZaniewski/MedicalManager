FROM openjdk:8-jdk-alpine
VOLUME /tmp
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/medManager-1.0-SNAPSHOT.jar"]