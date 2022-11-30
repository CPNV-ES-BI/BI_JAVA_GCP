# Hello_World-Microservice

## Create the docker image from the code
```
docker build --build-arg JAR_FILE=build/libs/\*.jar -t cpnv/hello-world-microservice .
```