# arolaz

Spring-Boot e-commerce app with Microservices architecture using Spring Cloud

Technologies
Java 11
Spring Boot 2
Spring Cloud 
Docker

Features:
User authentication and access control using Spring Security with Json Web Token
Spring actuator to monitor API usage including /health endpoint
OpenAPI/Swagger for API documentation
Spring Boot based Restful API including HATEOAS, Validation, Exception Handling
App uses h2 in memory database for testing and mysql database while app is running
Adding product to shopping cart without logging into application
Interacting with RabbitMQ server to pubish and subscribe messages for update in order details using Spring AMQP's RabbitTemplate.
Admin role can add/update/delete products
Hibernate Level 2 caching with EhCache
Spring Cloud components
Eureka for Microservice Registration and Discovery and Dynamic scaling
Netflix-zuul for API Gateway
Feign for declarative REST client for Microservices
Client side load balancing using Ribbon
Hystrix for fault tolerance
Distributed Tracing using Spring Cloud Sleuth and Zipkin
Build and Deploy
Running apps independently with maven installed:
Change directory to Spring Cloud projects and run as Spring Boot application
Database setup
For coupon-service application
Create database 'coupondb' in mongodb
Update application-properties at src/main/resources with database properties
For shopbiz-api application
create database 'shopbiz' in mysql
Update mysql credentials in application.properties located at src/main/resources
All tables will be created and intial data will be imported automatically from data.sql file by Spring Boot
Navigate to shopbiz directory

-$ cd shopbiz-api

Run as spring-boot application

$ mvn spring-boot:run

Swagger documentation available at - http://localhost:8080/shopbiz/swagger-ui.html#/

In browser navigate to http://localhost:8080/shopbiz/

To run frontend with Angular CLI and NodeJS
navigate to shopbiz-ui
$ cd shopbiz-ui
$ npm install
$ ng serve
Frontend will be running at http://localhost:4200

Deploy Using Docker
Run docker compose from project root

-$ docker-compose up --build

This will deploy whole application with microservices where nginx will serve UI and redirect to backend using proxy and mysql and mongodb containers will be running with persistent volume to store data across container lifecycle.

Navigate to http://localhost to view running app

To login
Customer role
email - customer@test.com
password - password
Admin role
email - admin@test.com
password - password
