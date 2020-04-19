# employee-service

### High-Level Design

![Imgur](https://i.imgur.com/PIBTRZF.png?1)

### Core Components

#### Database

Postgres database is used.
There is only one table "employee" which stores the employee data.

#### Elastic Search Cluster

Elastic search is used to support search functionality on employees data.
Used elastic search because it helps in searching the documents efficiently and faster when compared to search on DB.

#### Employee microservice

This service talks to DB, elastic search cluster and payroll service which stores the employee data along with salary.

#### Technologies used:

Java8

PostgresDB

Rest High Level Elastic Search Client

Hystrix for circuit breaker

spring-boot actuator for analyzing the metrics

Junit5 and mockito for unit tests


### Postman collection

https://www.getpostman.com/collections/d0d4b28f377da476c605

### Steps to run the application

$ mvn clean install

$ docker pull 19930614/employee-service

$ docker run -p 80:80 <docker_id>