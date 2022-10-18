# syntax=docker/dockerfile:1.2

FROM eed3si9n/sbt:jdk11-alpine as build
WORKDIR /app
COPY . /app
RUN sbt assembly

FROM openjdk:18-slim
RUN mkdir /app
COPY --from=build /app/target/scala-2.12/brighton-tide-post-assembly-0.1.0-SNAPSHOT.jar /app/tide-post.jar
CMD ["java", "-jar", "/app/tide-post.jar"]
