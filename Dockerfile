FROM gradle:latest as builder
WORKDIR /usr/src/app
COPY src ./src
COPY build.gradle.kts .
RUN ["gradle", "bootJar"]

FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /usr/src/app/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]