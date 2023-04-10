package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner  {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
         /*
        at first use of the app, if no user table exists then create admin user with:
        'admin@gmail.com' username and 'admin' password
                */

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            User user = new User("admin user", "admin@gmail.com",
                    passwordEncoder.encode("admin"),
                    Arrays.asList(new Role("ROLE_ADMIN")));
            userRepository.save(user);
            user = new User("user user", "user@gmail.com",
                    passwordEncoder.encode("user"),
                    Arrays.asList(new Role("ROLE_USER")));
            userRepository.save(user);
        }
    }

}
