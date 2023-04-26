package com.example.jmsrabbitmq.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.File;

@Configuration
public class MailConfig {

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("config.properties");) {
            props.load(inputStream);
    
            System.setProperty("EMAIL_USERNAME", props.getProperty("EMAIL_USERNAME"));
            System.setProperty("EMAIL_PASSWORD", props.getProperty("EMAIL_PASSWORD"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
}