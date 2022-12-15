# Running Liquibase in the Cloud using Kubernetes

This project contains a demo for running `Liquibase` migration as an [init container](https://kubernetes.io/docs/concepts/workloads/pods/init-containers/)
in a Kubernetes environment


## Getting started

Before you're able to run this demo, you need the following already setup on your development environment

- Kubernetes: either one of the following
  - [kind](https://kind.sigs.k8s.io/)
  - [minikube](https://minikube.sigs.k8s.io/docs/start/)
  - [helm](https://helm.sh/)
  - [Docker Desktop](https://docs.docker.com/desktop/kubernetes/) with Kubernetes enabled on Mac or PC
- [cURL](https://curl.se/)


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


## Preparing the Application to run in Kubernetes

The following sections contain instructions on how to:

1. Build a native docker image
2. Install Postgres operator


### Building a native docker image

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
```

This will result in a docker image with name `${quarkus.container-image.group}/${quarkus.container-image.name}:${version}`.
The following properties are defined in `application.properties`

```properties
quarkus.container-image.group=juliuskrah
quarkus.container-image.name=liquibase-kubernetes
```


### Install PostgreSQL Operator

The following commands will be issued to install the [PostgreSQL Operator](https://postgres-operator.readthedocs.io/en/latest/).

```shell script
# add repo for postgres-operator
helm repo add postgres-operator-charts https://opensource.zalando.com/postgres-operator/charts/postgres-operator

# install the postgres-operator
helm install postgres-operator postgres-operator-charts/postgres-operator

# add repo for postgres-operator-ui
helm repo add postgres-operator-ui-charts https://opensource.zalando.com/postgres-operator/charts/postgres-operator-ui

# install the postgres-operator-ui
helm install postgres-operator-ui postgres-operator-ui-charts/postgres-operator-ui
```


### Start the Database Cluster

We will start the PostgreSQL database cluster `example-liquibase-cluster`

```shell script
kubectl apply -f k8s/data
```

Check all components are coming up

```shell script
# check the deployed cluster
kubectl get postgresql

# check created database pods
kubectl get pods -l application=spilo -L spilo-role

# check created service resources
kubectl get svc -l application=spilo -L spilo-role
```


### Start the Application Workload

Now we're ready to start our application

```shell script
kubectl apply -f k8s/app
```

Check all components are coming up

```shell script
kubectl get pods -l 'app.kubernetes.io/name=liquibase-on-kubernetes'
```

You should see output similar to what is below

```shell script
NAME                                       READY   STATUS     RESTARTS   AGE
liquibase-on-kubernetes-667678855d-qmwdj   0/1     Init:0/1   0          4s
```

Take note of the name of the pod and run the command to follow the logs of the liquibase migration

> Replace &lt;pod-name&gt; with the pod name noted earlier

```shell script
kubectl logs --follow pod/<pod-name> -c liquibase-migration
```

If all is good, you can port forward

```shell script
kubectl port-forward services/liquibase-on-kubernetes 8080:80
```
