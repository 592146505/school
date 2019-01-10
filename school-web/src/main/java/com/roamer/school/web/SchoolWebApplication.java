package com.roamer.school.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.roamer.school")
public class SchoolWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolWebApplication.class, args);
    }

}

