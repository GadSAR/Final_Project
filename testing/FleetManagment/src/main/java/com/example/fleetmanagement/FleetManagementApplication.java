package com.example.fleetmanagement;

import com.example.fleetmanagement.repository.FleetManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FleetManagementApplication implements CommandLineRunner {

    @Autowired
    FleetManagementRepository fleetManagementRepository;

    public static void main(String[] args) {
        SpringApplication.run(FleetManagementApplication.class, args);
    }
    @Override
    public void run(String... args) {

    }
}
