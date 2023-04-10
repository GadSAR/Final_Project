package com.example.demo.config;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
The AppSecurity class is a configuration class for Spring Security in a Java-based Spring Boot application.
It is responsible for setting up various security-related aspects, such as password encoding,
 authentication, and authorization.

 This class sets up the security configuration for the application, including password encoding,
  allowing CORS (Cross-Origin Resource Sharing), disabling CSRF (Cross-Site Request Forgery) protection,
   configuring access control to various URL patterns, adding a custom JWT authentication filter,
    and ignoring static resources (CSS and images) for security purposes.
 */
// Import statements

// @Configuration and @EnableWebSecurity annotations indicate that this class is a Spring configuration class
// and that it should enable web security.
@Configuration
@EnableWebSecurity
public class AppSecurity {

    // Instance variables for the custom user details service and JWT utility class
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // Constructor injection of the custom user details service and JWT utility class
    public AppSecurity(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    // Configuring a password encoder to use for encoding passwords
    // In this case, BCryptPasswordEncoder is used for hashing passwords.
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuring the security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                // Configuring authorization for HTTP requests
                .authorizeHttpRequests()

                // Permitting access to the login page and its sub-pages for all users
                .requestMatchers("/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // Adding a custom JWT authentication filter before the default UsernamePasswordAuthenticationFilter
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class);

        // Building and returning the security filter chain
        return http.build();
    }

    // Configuring web security to ignore requests to the CSS and CSS images directories
    // This is useful for allowing unauthenticated access to static resources such as styles and images.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/css_images/**");
    }
}
