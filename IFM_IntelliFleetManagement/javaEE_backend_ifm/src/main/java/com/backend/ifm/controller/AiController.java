package com.backend.ifm.controller;

import com.backend.ifm.service.ExecuteScriptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    @Autowired
    private ExecuteScriptService executeScriptService;


    @GetMapping("/model1_build")
    public String model1_build() {
        executeScriptService.executeScript(1, "build");
        return "Model 1 build";
    }
    @GetMapping("/model1_predict")
    public String model1_predict() {
        executeScriptService.executeScript(1, "predict");
        return "Model 1 predict";
    }
    @GetMapping("/model2_build")
    public String model2_build() {
        executeScriptService.executeScript(2, "build");
        return "Model 2 build";
    }
    @GetMapping("/model2_predict")
    public String model2_predict() {
        executeScriptService.executeScript(2, "predict");
        return "Model 2 predict";
    }
    @GetMapping("/model3_predict")
    public String model3_predict() {
        executeScriptService.executeScript(3, "predict");
        return "Model 3 predict";
    }
}
