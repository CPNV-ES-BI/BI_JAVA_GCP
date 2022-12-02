FROM gradle:jdk19-alpine as base
WORKDIR /app
COPY --chown=gradle:gradle . .
COPY gradle/ gradle
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle --refresh-dependencies

FROM base as test
CMD ["gradle", "test"]

FROM base as build
RUN ./gradlew package

FROM eclipse-temurin:19-jdk-alpine as production
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]