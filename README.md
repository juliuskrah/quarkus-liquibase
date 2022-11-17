# Running Liquibase in the Cloud using Kubernetes

This project contains a demo for running `Liquibase` migration as an init container in a Kubernetes environment

## Getting started

Before you're able to run this demo, you need the following already setup on your development environment

- Kubernetes
  - [kind](https://kind.sigs.k8s.io/)
  - [minikube](https://minikube.sigs.k8s.io/docs/start/)
  - [Docker Desktop](https://docs.docker.com/desktop/kubernetes/) with Kubernetes enabled on Mac or PC
- [cURL](https://curl.se/)

## Building a native docker image

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

The following operations have been defined

1. GET http://localhost:8080/clients/:id
   
   ```shell script
   curl --location --request GET 'http://localhost:8080/clients/ce74d8f2-ef49-4f2a-b5cc-52ef30046d40'
   ```

2. GET http://localhost:8080/clients

   ```shell script
   curl --location --request GET 'http://localhost:8080/clients'
   ```

3. POST http://localhost:8080/clients

   ```shell script
   curl --location --request POST 'http://localhost:8080/clients' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "name": "evil corp",
       "code": "EVIL",
       "contactPerson": "Tyrell Wellick"
   }'
   ```

4. GET http://localhost:8080/clients/:code/code

   ```shell script
   curl --location --request GET 'http://localhost:8080/clients/acme/code'
   ```

5. GET http://localhost:8080/clients/:clientCode/services

   ```shell script
   curl --location --request GET 'http://localhost:8080/clients/acme/services'
   ```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/liquibase-demo-1.0.0-SNAPSHOT-runner`
