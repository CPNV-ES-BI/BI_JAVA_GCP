FROM openjdk:17-jdk-alpine as base

WORKDIR /app
COPY ./.mvn .mvn
COPY ./mvnw pom.xml ./
RUN chmod +x ./mvnw && ./mvnw dependency:resolve
COPY ./src ./src

FROM base as test
CMD ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run"]