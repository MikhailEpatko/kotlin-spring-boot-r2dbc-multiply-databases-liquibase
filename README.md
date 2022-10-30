# MVP Kotlin + Spring Boot + R2DBC + Multiply Databases + Liquibase

The project shows how to connect to multiple databases in a Spring Boot application with r2dbc driver and configure Liquibase for each of databases.

## Prerequisites

Installed:

- Docker Compose,
- JDK 17,
- Kotlin 1.7.20,
- IDE - for simple start application.

## How to start MVP

#### 1. Start databases in docker compose:

```
cd ./docker

docker compose up -d

```
#### 2. Verify that the services are up and running:
```
docker compose ps

```
#### 3. After a few minutes, if the state of any component isn’t Up, try:
```
docker compose up -d

```
or
```
docker compose restart <image-name>

```
#### 4. Start Application
Start application in the IDE.

After the application started it will start Liquibase migration scripts and write test data into the databases. Then it read test data from the databases, print it into the IDE console and finish the job.
