# BI JAVA GCP

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
git clone https://github.com/CPNV-ES-BI/BI_JAVA_GCP.git
```

## Dependencies

    - Spring Boot Starter
    - Spring Boot Starter Web
    - Spring Cloud GCP Starter
    - Spring Cloud GCP Starter Storage
    - Springdoc Openapi UI
    - Spring Boot Starter Test

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
This command will run all the tests in the DataObjectControllerTest class.

```
./mvnw test -Dtest=DataObjectControllerTest
```

### Run specific method tests
This command will run the test_DoesExist_ExistsCase_True test in the DataObjectControllerTest class.
To make this work, you add to separate the class name from the method name with a #.

```
./mvnw test -Dtest=DataObjectControllerTest#test_DoesExist_ExistsCase_True
```

## Run the docker image

Those commands create and run the docker using docker-compose.

### Run the production image

```
docker compose up developement
```
### Run the test image

```
docker compose up test
```

This command starts the image and run it as a container.
It runs all tests and report the result in the console.

## CI with Maven

During the project, a continous integration github action has been created to test the unit test from the project.

The configuration of the file can be find in the [wiki.](https://github.com/CPNV-ES-BI/BI_JAVA_GCP/wiki/CI-CD---With-docker)

## Directory structure

The directory structure of the project is as follows:

    - .github/workflows
        - main.yml
    - src
        - main
            - java
                - com
                    - cpnv
                        - bijavagcp
                            - api
                                - DataObjectController
                            - config
                                - GcpConfiguration
                            - exceptions
                                - ObjectAlreadyExistsException
                                - ObjectNotFoundException
                            - services
                                - DataObject
                                - DataObjectService
                            - BiJavaGcpApplication                            
            - resources
                - application.properties
                - gcp.properties
        - test
            - java
                - com
                    - cpnv
                        - bijavagcp
                            - services
                                - DataObjectControllerTest
    - docker-compose.yml
    - Dockerfile
    - README.md
    - mvnw
    - mvnw.cmd
    - pom.xml

The root directory contains all the configuration files for the project and the dockerfile while the src directory is split into two parts: main and test. 
The main directory contains the code of the application while the test directory contains the tests of the application.

## Branching

For branching, we will use the Gitflow branching model due to its simplicity and ease of use.
Wiki link for specification : [wiki](https://github.com/CPNV-ES-BI/BI_JAVA_GCP/wiki#branching)

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




