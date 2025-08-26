package com.iassure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IassureApplication {

    public static void main(String[] args) {
        SpringApplication.run(IassureApplication.class, args);
    }

}
