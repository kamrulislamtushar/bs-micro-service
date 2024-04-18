
## Getting Started 
Currently you may find here some examples of microservices implementation using different projects from Spring Cloud. All the examples are divided into the branches and described in a separated articles on my blog. Here's a full list of available examples:
1. (This example has been update to the latest version of Spring Cloud without Zuul) Using Spring Cloud Netflix **Eureka** as a discovery server, **Zuul** as a gateway, **OpenFeign** for communication and Spring Cloud Config Server.
2. Using Spring Cloud Alibaba **Nacos** as a discovery and configuration server, **Zuul** and **OpenFeign**.
3. Using Spring Cloud with Spring Boot support for **GraphQL** for building microservices, **Apollo** for inter-service communication and **Eureka** as a discovery server. 
4. Using Spring Boot and partially Spring Cloud for building microservices
### Usage

Build the apps with images (we need ji for `config-service` since it contains `curl`):
```shell
$ mvn clean package -Pbuild-image
```

Then run all the containers with `docker-compose`:
```shell
$ docker-compose up
```

In the most cases you need to have Maven and JDK8+. In the fourth example with OpenShift you will have to run **Minishift** on your machine. The best way to run the sample applications is with IDEs like IntelliJ IDEA or Eclipse.  

## Architecture

Our sample microservices-based system consists of the following modules:
- **gateway-service** - a module that Spring Cloud Netflix Zuul for running Spring Boot application that acts as a proxy/gateway in our architecture.
- **config-service** - a module that uses Spring Cloud Config Server for running configuration server in the `native` mode. The configuration files are placed on the classpath.
- **discovery-service** - a module that depending on the example it uses Spring Cloud Netflix Eureka or Spring Cloud Netlix Alibaba Nacos as an embedded discovery server.
- **user-service** - a module containing the first of our sample microservices that allows to perform CRUD operation on postgres repository of employees

