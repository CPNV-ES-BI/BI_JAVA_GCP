# Hello_World-Microservice

## Install Java JDK locally

You can download it from the Oracle [website](https://www.oracle.com/java/technologies/downloads/#jdk19-windows)

## Clone the project

```
git clone https://github.com/Nomeos/Hello_World-Microservice.git
```

## Build the projet locally

```
.\gradlew build
```

## Create the docker image from the code
```
docker build --build-arg JAR_FILE=build/libs/\*.jar -t cpnv/hello-world-microservice .
```