##  POC: Microservice Architecture Pattern using Spring Boot and netflix technologies

This project can be used as showcase or Proof of Concept to build and manage microservices using Spring Boot.

It includes:    

[Config Server]() => Spring Cloud Config  
[API GateWay]() => Zuul by Netflix  
[Service Discovery]() => Eureka by Netflix  
[Sample Spring Boot Application]()
  
### Overview

I have added all above mentioned micro services into one parent project just to make it easy to manage in one place. 
As it is just a POC project, it does not have much business logic abd heavy requirements. You can still run each 
micro service independently in order.  

Here are short overviews:  

Config Server    
[//]: # (This may be the most platform independent comment)  
[comment]: <> (This is a comment, it will not be included)  
[comment]: <> (in  the output file unless you use it in)  
[comment]: <> (a reference style link.)  

##### Basics  


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

#### Note
The followings are key points in configuration and management of config server. These are the ones  
I had hard time figuring out in the beginning while building micro service architecture in the company. 
There are might be some other and better solutions. Please pull request If you know one.  

  
1. If you want to store remote config in filesystem, 
besides providing file path: `spring.cloud.config.server.native.searchLocations=file:{path}`, 
you should also use `spring.profiles.active=native` profile.
2.You can just add `spring.cloud.config.server.bootstrap=true` to bootstrap.yml of config server to tell config server to get 
its configuration from remote file or repository on git. 