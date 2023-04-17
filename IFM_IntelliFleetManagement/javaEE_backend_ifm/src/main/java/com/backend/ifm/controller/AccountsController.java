package com.backend.ifm.controller;

import com.backend.ifm.entity.User;
import com.backend.ifm.repository.UserRepository;
import com.backend.ifm.service.AccountsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountsController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
