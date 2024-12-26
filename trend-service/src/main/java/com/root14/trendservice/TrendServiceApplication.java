package com.root14.trendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrendServiceApplication.class, args);
    }

}
