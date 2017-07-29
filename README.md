##  POC: Microservice Architecture Pattern using Spring Boot and netflix technologies

This project can be used as showcase or Proof of Concept to build and manage microservices using Spring Boot.

It includes:    

[Config Server]() => Spring Cloud Config  
[API GateWay]() => Zuul by Netflix  
[Service Discovery]() => Eureka by Netflix  
[Sample Spring Boot Application]()
  
### Config Server Overview: 

Once we run our config server at default 8888 port, we can get all configs using following rules in json format.  
 
**application** is the name of service defined by `spring.application.name` (also equal to `spring.cloud.config.name`) in config file.  
**profile** is the name of profiles set by `spring.profiles.active` on the client side , separated by comma  
**label** is the label name to pull remote config files. if it is git label is _master_ by default 
 
/{application}/{profile}[/{label}] such as http://localhost:8888/api-gateway/default  
/{application}-{profile}.yml  
/{label}/{application}-{profile}.yml  
/{application}-{profile}.properties  
/{label}/{application}-{profile}.properties    

#### Tweaks
If you want to store remote config in filesystem, besides providing git repo url you should also use `spring.profiles.active.native` profile