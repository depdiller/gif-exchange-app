FROM gradle:7.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
EXPOSE 8585
RUN mkdir /app
COPY --from=build /home/gradle/src /app
ENTRYPOINT [ "java", "-jar", "/app/build/libs/exchange-rate-0.0.1.jar" ]