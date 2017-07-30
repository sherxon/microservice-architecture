##  POC: Microservice Architecture Pattern using Spring Boot and netflix technologies

This project can be used as showcase or Proof of Concept to build and manage microservices using Spring Boot.

It includes:    

[Config Server]() => Spring Cloud Config  
[API GateWay]() => Zuul by Netflix  
[Service Registry]() => Eureka by Netflix  
[Sample Spring Boot Application]()
  
### Overview

I have added all above mentioned micro services into one parent project just to make it easy to manage in one place. 
As it is just a POC project, it does not have much business logic abd heavy requirements. You can still run each 
micro service independently.

Here are short overviews:  

**Config Server** is used to store configurations of all micro services in one centralized place. You can keep and change   
configuration of any micro service such as database credentials and network location in externalized place and restart the service to pull new configuration.  

**Api Gateway (Zuul)** is the implementation of Backend for Front-End pattern. Main motive to use Api Gateway is to have one edge service 
for clients and still manage a number of service instances and their locations (host+port) which change dynamically. 

**Service Registry** is a service that keeps track of all other micro service instances, their location and health. All micro 
services register themselves with their information to Service Registry at startup and is deregistered if Service Registry 
cannot reach at certain point. Client Services such as Api Gateway ask Service Registry for location of available micro service
instance. Eureka is used in this project as an implementation of Service Registry.

<!---
application vs bootstrap property files
different profiles cannot be used with properties
place all your source code inside certain package in all projects.
component scan problem
-->

##### Basics  
All micro services are spring boot applications with dependencies managed by maven. Each Service has bootstrap.yml 
file which stores minimum required configuration for each service.  


### Config Server Overview: 

Once we run our config server at default 8888 port, we can get all configs using following rules in json format.  
 
**application** is the name of service defined by `spring.application.name` (or `spring.cloud.config.name`) in config file.  
**profile** is the name of profiles set by `spring.profiles.active` on the client side , separated by comma  
**label** is the label name to pull remote config files. if remote config location is git, the label is _master_ by default.   
 
`/{application}/{profile}[/{label}]` such as http://localhost:8888/api-gateway/default  
`/{application}-{profile}.yml`   
`/{label}/{application}-{profile}.yml`  
`/{application}-{profile}.properties`  
`/{label}/{application}-{profile}.properties`  

#### Note:
The followings are key points in configuration and management of config server. These are the ones  
I had hard time figuring out in the beginning while building micro service architecture in the company. 
There are might be some other and better solutions. Please pull request If you know one.  

  
1. If you want to store remote config in filesystem, 
besides providing file path: `spring.cloud.config.server.native.searchLocations=file:{path}`, 
you should also use `spring.profiles.active=native` profile.  
2. You can just add `spring.cloud.config.server.bootstrap=true` to bootstrap.yml of config server to tell config server to get 
its configuration from remote file or repository on git.  
3. Use `@RefreshScope` annotation in each client of config server, including config server itself if you want it to load its configuration 
from remote location.  
4. 

### Service Registry Overview:
 
 

