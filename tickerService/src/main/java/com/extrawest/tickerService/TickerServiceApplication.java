package com.extrawest.tickerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TickerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TickerServiceApplication.class, args);
    }

}
