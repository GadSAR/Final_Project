package com.backend.ifm.service;

import org.springframework.stereotype.Service;
import com.backend.ifm.entity.Role;
import com.backend.ifm.entity.User;
import com.backend.ifm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;


@Service
public class AccountsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public void createAccount(String name, String email, String password, String role) {
        User user = new User(
        name, 
        email, 
        passwordEncoder.encode(password),
        Arrays.asList(new Role(role)));
        userRepository.save(user);
    }

   public void createUser(String name, String email, String password) {
        createAccount(name, email, password, "ROLE_USER");
   }

    public void createAdmin(String name, String email, String password) {
        createAccount(name, email, password, "ROLE_ADMIN");
    }


}