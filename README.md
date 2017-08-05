##  POC: Microservice Architecture Pattern using Spring Boot and netflix technologies and helpful hints

This project can be used as showcase or Proof of Concept to build and manage micro services using Spring Boot.

It includes:    

[Config Server]() => Spring Cloud Config  
[API GateWay]() => Zuul by Netflix  
[Service Registry]() => Eureka by Netflix  
[Sample Spring Boot Application]()

  If you are not familiar with micro service architecture pattern, I recommend reading [Microservice Architecture](http://microservices.io/patterns/microservices.html)   
  first by Chris Richardson. 
  
### General Overview

All micro services are spring boot applications with dependencies managed by maven. Each Service has bootstrap.yml 
file which stores minimum required configuration for each service.  

I have added all above mentioned micro services into one parent project just to make it easy to manage in one place  
as it is just a POC project, without much business logic abd heavy requirements. You can still run each 
micro service independently.

This is [Config First Bootstrap](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html#config-first-bootstrap), so you 
should run Config Server first and Eureka Server second then Api Gateway and other micro services. 
You may see runtime exceptions thrown by Config Server when you run it until Eureka Server runs and Config server register itself.

#### General Hints
* Use `.yml` files instead of `.properties`, so you can write more than one profile configurations for each service with `---` 
between them in one yml file and of course shorter code.     
* Keep all your source code always in certain package, not in source root to make it scanable by Spring DI   

### Config Server OverView 

**Config Server** is used to store configurations of all micro services in one centralized place. You can keep and change   
configuration of any micro service such as database credentials and network location in externalized place and restart the service 
to pull new configuration.  

To implement externalized configuration pattern I used spring cloud config server and spring cloud config clients. To make 
any spring boot application a config server you can just add one maven starter dependency and `@EnableEurekaServer` on configuration class. 
Spring cloud config server serves clients over rest api. Clients need to add spring-cloud-config-client dependency and 
config server or eureka server ( if you want config server to be discovered by eureka) location in bootstrap.yml 
 
Once we run our config server at default 8888 port, we can get all configs using following rules in json format.  
 
**application** is the name of service defined by `spring.application.name` (or `spring.cloud.config.name`) in config file.  
**profile** is the name of profiles set by `spring.profiles.active` on the client side , separated by comma  
**label** is the label name to pull remote config files. if remote config location is git, the label is _master_ by default.   
 
`/{application}/{profile}[/{label}]` such as http://localhost:8888/api-gateway/default  
`/{application}-{profile}.yml`   
`/{label}/{application}-{profile}.yml`  
`/{application}-{profile}.properties`  
`/{label}/{application}-{profile}.properties`  

##### Attention

1. If you want to store remote config in filesystem, 
besides providing file path: `spring.cloud.config.server.native.searchLocations=file:{path}`, 
you should also use `spring.profiles.active=native` profile.  
2. You can just add `spring.cloud.config.server.bootstrap=true` to bootstrap.yml of config server to tell config server to get 
its configuration from remote file or repository on git.  
3. Use `@RefreshScope` annotation in each client of config server, including config server itself, if you want changed configurations take an 
affect by sending post request  `/refresh` endpoints. 
from remote location.  
4. Config Server does not cache configurations, it reads from remote location (git or filesystem) for each request. 
5. 




### Service Registry Overview:
  
**Service Registry** is a service that keeps track of all other micro service instances, their location and health. All micro 
services register themselves with their information to Service Registry at startup and is deregistered if Service Registry 
cannot reach at certain point. Client Services such as Api Gateway ask Service Registry for location of available micro service
instance. Eureka is used in this project as an implementation of Service Registry.  

<!---
enable self preservation
-->

I dont have to write a lot about Eureka Server, you can read this awesome [Spring Cloud Netflix Eureka - The Hidden Manual](http://blog.abhijitsarkar.org/technical/netflix-eureka/) 
blog by Abhijit Sarkar and get thorough understanding about Eureka Server.   

In this project, Eureka server loads its configuration from Config Server and at the same time Config Server is eureka client.

 

### Api Gateway Overview 

**Api Gateway (Zuul)** is the implementation of Backend for Front-End pattern. Main motive to use Api Gateway is to have one edge service 
for clients and still manage a number of service instances and their locations (host+port) which change dynamically. 




#### Disclaimer
This project is Shareware and distributed under GSL(General Sharewire Licence), which means you can do whatever you want 
with it once you share it with at least one person :). Most of ideas and "hints" are just my opinions
and solutions to problems I have faced up. Thanks to stackoverflow and netflix community.  
    
If you think improvement or a change is needed in any part of the project, feel to contribute !      
