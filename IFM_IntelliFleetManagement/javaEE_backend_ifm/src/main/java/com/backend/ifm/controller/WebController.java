package com.backend.ifm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public String hello() {
        return "Welcome to IFM's backend magic";
    }
}
