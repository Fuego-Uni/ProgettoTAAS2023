FROM openjdk:17
ARG JAR_FILE=target/service-discovery-server-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]