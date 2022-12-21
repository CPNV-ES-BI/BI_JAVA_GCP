# Hello_World-Microservice

The goal of this spring boot microservice is to use Google Cloud Platform as a data source 
to carry out certain business intelligence-related activities.


## Versions

    - Java 17
    - Spring Boot 2.7.6
    - Maven 4.0.0
    - Docker 20.10.17

## Getting Started

This project is a simple Microservice. It is a Spring Boot application that can be run locally or in a Docker container.
The purpose of this project is to build a simple microservice using the Google cloud platform to perform Business Intelligence .

## Install Java JDK locally

You can download it from the Oracle [website](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)

## Clone the project

```
git clone https://github.com/Nomeos/Hello_World-Microservice.git
```

## Dependencies

    - Spring Boot Starter
    - Spring Boot Starter Test
    - Spring Boot Starter Web

## Build the project locally

Run the spring boot application locally using the maven wrapper.

```
./mvnw spring-boot:run
```

## Build the tests locally

### Run all tests
This command will run all the tests in the project.

```
./mvnw test
```

### Run specific class tests
This command will run all the tests in the HelloWorldControllerTest class.

```
./mvnw test -Dtest=HelloWorldControllerTest
```

### Run specific method tests
This command will run the testHelloWorld test in the HelloWorldControllerTest class.
To make this work, you add to separate the class name from the method name with a #.

```
./mvnw test -Dtest=HelloWorldControllerTest#testHelloWorld
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

## Directory structure

The directory structure of the project is as follows:

    - .github/workflows
        - main.yml
    - src
        - main
            - java
                - ch
                    - cpnv
                        - BIJAVAGCP
                            - BiJavaGcpApplication.java
                            - DataObject.java
                            - DataObjectControler.java
            - resources
                - application.properties
        - test
            - java
                - ch
                    - cpnv
                        - BIJAVAGCP
                            - BiJavaGcpApplicationTests.java
                            - DataObjectTests.java
    - Dockerfile
    - README.md
    - mvnw
    - mvnw.cmd
    - pom.xml

The root directory contains all the configuration files for the project and the dockerfile while the src directory is split into two parts: main and test. 
The main directory contains the code of the application while the test directory contains the tests of the application.

## Contribute

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right"><a href="#readme-top">back to top</a></p>

## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right"><a href="#readme-top">back to top</a></p>

## Contact

- [Nomeos](https://github.com/Nomeos)
- [robielcpnv](https://github.com/robielcpnv)




