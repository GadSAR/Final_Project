package com.backend.ifm;

import com.backend.ifm.entity.Role;
import com.backend.ifm.entity.User;
import com.backend.ifm.repository.RoleRepository;
import com.backend.ifm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BackendIfmApplication implements CommandLineRunner  {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BackendIfmApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            User user = new User("admin user", "admin@gmail.com",
                    passwordEncoder.encode("admin"),
                    List.of(new Role("ROLE_ADMIN")));
            userRepository.save(user);
            user = new User("user user", "user@gmail.com",
                    passwordEncoder.encode("user"),
                    List.of(new Role("ROLE_USER")));
            userRepository.save(user);
        }
    }
}
