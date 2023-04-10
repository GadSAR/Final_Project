package com.backend.ifm.controller;

import com.backend.ifm.service.AccountsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountsService accountsService;


    /*@GetMapping("/model1_build")
    public String model1_build() {
        accountsService.createAdmin(1, "build");
        return "Model 1 build";
    }*/

}
