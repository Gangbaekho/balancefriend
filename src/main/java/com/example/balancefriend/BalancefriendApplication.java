package com.example.balancefriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BalancefriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalancefriendApplication.class, args);
    }

}
