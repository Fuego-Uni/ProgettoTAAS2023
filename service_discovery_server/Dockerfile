# #----
# # Build stage
# #----
# FROM maven:3.8.6-openjdk-18-slim AS buildstage
# # Copy only pom.xml of your projects and download dependencies
# COPY pom.xml .
# RUN mvn -B -f pom.xml dependency:go-offline
# # Copy all other project files and build project
# COPY . .
# RUN mvn -B install

# #----
# # Final stage
# #----
# FROM openjdk:17
# COPY --from=buildstage ./target/*.jar ./
# ENV JAVA_OPTS ""
# CMD [ "bash", "-c", "java ${JAVA_OPTS} -jar *.jar -v"]

FROM openjdk:17
COPY target/*.jar /app/*.jar
ENV JAVA_OPTS ""
CMD [ "bash", "-c", "java ${JAVA_OPTS} -jar /app/*.jar -v"]