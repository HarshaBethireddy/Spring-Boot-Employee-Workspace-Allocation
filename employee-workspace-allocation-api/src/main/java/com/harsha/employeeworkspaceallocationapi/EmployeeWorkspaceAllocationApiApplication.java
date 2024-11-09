package com.harsha.employeeworkspaceallocationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmployeeWorkspaceAllocationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeWorkspaceAllocationApiApplication.class, args);
    }

}
