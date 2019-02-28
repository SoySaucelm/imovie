package com.imovie.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@SuppressWarnings(value={"all"})
public class ImovieUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImovieUserApplication.class, args);
    }

}
