package com.backend.ifm.controller;

import com.backend.ifm.config.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        return "index";
    }

    @PostMapping("/contact_us")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
    }

}
