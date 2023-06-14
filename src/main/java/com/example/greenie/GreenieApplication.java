package com.example.greenie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GreenieApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenieApplication.class, args);
    }

}
