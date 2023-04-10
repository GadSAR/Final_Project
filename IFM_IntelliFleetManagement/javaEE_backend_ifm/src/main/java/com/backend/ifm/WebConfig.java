package com.backend.ifm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
This class is a configuration class used to handle Cross-Origin Resource Sharing (CORS) settings for the application.
It is annotated with @Configuration, indicating that it is a Spring configuration class.

The corsConfigurer method is a bean factory method that creates a WebMvcConfigurer instance.
It returns an anonymous implementation of the WebMvcConfigurer interface, which allows customizing the CorsRegistry settings.
Inside the WebMvcConfigurer implementation, the addCorsMappings method is overridden to configure CORS settings.
The addCorsMappings method adds a CORS mapping for all paths in the application (/**),
 which means that CORS settings will be applied to all endpoints.
The allowedOrigins method is called to allow requests from the specified origin (http://localhost:3000).
This is useful for development, as the frontend might run on a different port than the backend.
The allowedMethods method is called to allow specified HTTP methods for CORS requests: GET, POST, PUT, DELETE, and OPTIONS.
The allowedHeaders method is called to allow any headers in CORS requests by specifying the wildcard *.
The allowCredentials method is called with true to allow sending and receiving cookies in CORS requests,
 enabling cross-origin authentication and session management.
 */
// Configuration class to handle CORS settings for the application
@Configuration
public class WebConfig {

    // Bean for configuring CORS settings
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // Return an anonymous implementation of WebMvcConfigurer
        return new WebMvcConfigurer() {

            // Override the addCorsMappings method to configure CORS settings
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Add a CORS mapping for all paths in the application
                registry.addMapping("/**")
                        // Allow requests from the specified origin (localhost:3000)
                        .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                        // Allow specified HTTP methods for CORS requests
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Allow any headers in CORS requests
                        .allowedHeaders("*")
                        // Allow sending and receiving cookies in CORS requests
                        .allowCredentials(true);
            }
        };
    }
}
