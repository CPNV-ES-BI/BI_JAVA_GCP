# Hello_World-Microservice

## Versions

    - Java 19.0.1

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

First, you can build the base image with the following command:
```
docker build --no-cache -t cpnv/hello-world-microservice-base --target base .
```

This command build an image in the docker and specify the target to build.
The target is the stage of the dockerfile. In this case, the target is the base stage. And give a name to the image.

Then, you can build the test image with the following command:
```
docker build --no-cache -t cpnv/hello-world-microservice-test --target test .
```

## Run the docker image

### Run the base image

```
docker run -it --rm --name hello-world-base cpnv/hello-world-microservice-base
```
### Run the test image

```
docker run -it --rm --name hello-world-test cpnv/hello-world-microservice-test
```

This command starts the image and run it as a container.
We specify the name of the container, springboot-test in this case, and the image to run.
