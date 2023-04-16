package com.backend.ifm;

import com.backend.ifm.repository.RoleRepository;
import com.backend.ifm.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BackendIfmApplication implements CommandLineRunner  {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountsService accountsService;

    public static void main(String[] args) {
        SpringApplication.run(BackendIfmApplication.class, args);
    }

    @Override
    public void run(String... args) {

        if (roleRepository.findByName("ROLE_USER") == null) {
            accountsService.createAdmin
                    ("admin user", "exemple", "admin@gmail.com", "admin");
            accountsService.createUser
                    ("user user", "exemple","user@gmail.com", "user");
        }
    }
}
