package com.backend.ifm.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
