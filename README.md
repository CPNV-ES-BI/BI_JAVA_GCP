# Hello_World-Microservice

## Versions

    - Java 19.0.1

## Install Java JDK locally

You can download it from the Oracle [website](https://www.oracle.com/java/technologies/downloads/#jdk19-windows)

## Clone the project

```
git clone https://github.com/Nomeos/Hello_World-Microservice.git
```

## Build the project locally

```
./mvnw spring-boot:run
```

## Create the docker image from the code

First, you can build the production image with the following command:
```
docker build --no-cache -t cpnv/hello-world-microservice-base .
```

This command build an image in the docker and specify the target to build.
The target is the stage of the dockerfile. In this case, the target is the base stage. And give a name to the image.

Then, you can build the test image with the following command:
```
docker build --no-cache -t cpnv/hello-world-microservice-test --target test .
```

## Run the docker image

### Run the production image

```
docker run -it --rm --name hello-world-base cpnv/hello-world-microservice-base
```
### Run the test image

```
docker run -it --rm cpnv/hello-world-microservice-test
```

This command starts the image and run it as a container.
It runs all tests and report the result in the console.

