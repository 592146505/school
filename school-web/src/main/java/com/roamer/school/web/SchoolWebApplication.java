package com.roamer.school.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.roamer.school")
@EntityScan(basePackages = "com.roamer.school.**.entity")
@EnableJpaRepositories(basePackages = "com.roamer.school.**.dao")
public class SchoolWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolWebApplication.class, args);
    }

}

