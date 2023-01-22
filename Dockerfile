FROM openjdk:17-jdk-alpine as base

WORKDIR /app
COPY ./.mvn .mvn
COPY ./mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw dependency:go-offline -B
COPY ./src ./src
RUN ./mvnw compile

FROM base as test
CMD ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run"]