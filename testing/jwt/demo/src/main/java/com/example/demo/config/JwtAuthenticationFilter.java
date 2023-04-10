package com.example.demo.config;

/*
The JwtAuthenticationFilter class is a custom filter class that extends the OncePerRequestFilter class
 provided by the Spring Security framework. This filter is responsible for intercepting incoming HTTP requests,
  extracting the JWT token from the request header, and validating the token.
If the token is valid, it sets the authentication information in the Spring Security context.

OncePerRequestFilter: A base class provided by the Spring Security framework for filters that are executed once per request.
JwtUtil: A custom utility class for JWT token processing and validation.
UserDetailsService: An interface provided by the Spring Security framework for loading user-specific data.
doFilterInternal(): A method that must be implemented by subclasses of OncePerRequestFilter.
This method contains the logic for processing incoming requests, extracting the JWT token, and validating it.
filterChain.doFilter(request, response): A method call that continues with the next filter in the filter chain.
This is called after processing the JWT token and setting the authentication information in the Spring Security context.
This custom filter helps in authenticating users based on the JWT token present in the incoming request headers,
 ensuring that only authenticated users can access protected resources.
 */
import com.example.demo.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Custom filter class that extends the OncePerRequestFilter provided by Spring Security
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Instance variables for the JwtUtil class and the custom user details service
    private JwtUtil jwtUtil;
    private UserDetailsService customUserDetailsService;
    private User user;

    // Constructor that initializes the JwtUtil and custom user details service instance variables
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    // This method is called once per request and is responsible for processing the authentication logic
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header from the incoming request
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // If the Authorization header is present and starts with "Bearer ",
        // extract the JWT token and the username from the token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwtToken);
        }

        // If the username is not null and there is no authentication information
        // in the Spring Security context, proceed with the authentication process
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the custom user details service
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Validate the JWT token with the user details
            if (jwtUtil.validateToken(jwtToken, user)) {
                // Create an authentication token and set its details
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication information in the Spring Security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue with the next filter in the filter chain
        filterChain.doFilter(request, response);
    }

}
