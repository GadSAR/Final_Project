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
        System.out.println("Loading config.properties from " + new File("config.properties").getAbsolutePath());

        try (InputStream inputStream = new FileInputStream("config.properties")) {
            props.load(inputStream);
            System.out.println(props);

            System.setProperty("EMAIL_USERNAME", props.getProperty("EMAIL_USERNAME"));
            System.setProperty("EMAIL_PASSWORD", props.getProperty("EMAIL_PASSWORD"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
}