package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sherali Obidov.
 */
@SpringBootApplication
@EnableConfigServer
@RefreshScope
@RestController
public class ConfigApplication {

    @Value("${message.body}")
    private String message;

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    // message is located in remote config file
    @GetMapping("/config-test")
    public String home() {
        return message;
    }

}
