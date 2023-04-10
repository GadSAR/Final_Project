package com.example.demo.controller;

import com.example.demo.config.AuthenticationRequest;
import com.example.demo.config.AuthenticationResponse;
import com.example.demo.config.JwtUtil;
import com.example.demo.entity.User;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Role;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        UserDetails userDetails = null;
        try {
            userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Validate the password (replace with your own validation logic)
        if (passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
            // If the credentials are valid, generate an access token using JwtUtil
            User user = new User();
            user.setName(authenticationRequest.getUsername());
            user.setPassword(authenticationRequest.getPassword());
            user.setRoles(userDetails.getAuthorities().stream().map(authority -> new Role(null, authority.getAuthority(), null)).collect(Collectors.toList()));
            String jwtToken = jwtUtil.generateToken(user);


            /* Retrieve the user's roles from the UserDetails object
            and set them in the User object
             */
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            // Convert the authorities to a list of Role objects
            List<Role> roles = authorities.stream()
                    .map(authority -> new Role(null, authority.getAuthority(), null))
                    .collect(Collectors.toList());
            // Set the user's roles
            user.setRoles(roles);


            // Return the access token in an AuthenticationResponse object
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        } else {
            // If the credentials are invalid, return an unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Endpoint for logging out a user
    /*
    This code defines a REST endpoint for logging out a user.
    The endpoint is a POST request mapped to /api/auth/logout.
    The method logout accepts an AuthenticationResponse object containing the JWT token as the request body.
    The JWT token is extracted from the AuthenticationResponse.
    The token's expiration date is obtained using the JwtUtil class.
    The tokenBlacklistService.addToBlacklist method is called to add the token to the blacklist,
     making it invalid for further use until it expires.
    If the token is successfully added to the blacklist,
     a success message "Logged out successfully" is returned with an HTTP status code of 200 (OK).
    If an exception is caught during this process,
     the method returns an HTTP status code of 401 (Unauthorized) along with an error message "Unable to logout".
     */
    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> logout(@RequestBody AuthenticationResponse authenticationResponse) {
        try {
            // Extract the JWT token from the authentication response
            String token = authenticationResponse.getJwtToken();
            // Get the expiration date of the token
            Instant expiration = jwtUtil.getExpirationDateFromToken(token);
            // Add the token to the blacklist, making it invalid for further use
            tokenBlacklistService.addToBlacklist(token, expiration);
            // Return a success message
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            // If an error occurs, return an unauthorized status with an error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unable to logout");
        }
    }


    // You can use a password encoder to compare the raw password with the encoded one
    private boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

