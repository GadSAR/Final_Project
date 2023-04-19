package com.backend.ifm.controller;

import com.backend.ifm.config.*;
import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.Role;
import com.backend.ifm.entity.User;
import com.backend.ifm.service.AccountsService;
import com.backend.ifm.service.CustomUserDetails;
import com.backend.ifm.service.CustomUserDetailsService;
import com.backend.ifm.service.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AccountsService accountsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;


    public AuthenticationController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        CustomUserDetails userDetails;
        try {
            userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {

            User user = new User();
            user.setName(userDetails.getName());
            user.setEmail(authenticationRequest.getEmail());
            user.setPassword(authenticationRequest.getPassword());
            user.setRoles(userDetails.getAuthorities().stream().map(authority -> new Role(null, authority.getAuthority(), null)).collect(Collectors.toList()));
            user.setCompanies(userDetails.getCompanies().stream().map(company -> new Company(null, company.getAuthority(), null)).collect(Collectors.toList()));
            String jwtToken = jwtUtil.generateToken(user);

            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/auth/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        try {
            if (customUserDetailsService.userExists(registerRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with the provided email already exists");
            }

            accountsService.createAdmin
                    (registerRequest.getUsername(),
                            registerRequest.getCompany(),
                            registerRequest.getEmail(),
                            registerRequest.getPassword());

            return ResponseEntity.ok("Registration successful");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register user");
        }
    }


    @PostMapping("/auth/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            if (customUserDetailsService.userExists(registerRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with the provided email already exists");
            }

            accountsService.createUser
                    (registerRequest.getUsername(),
                            registerRequest.getCompany(),
                            registerRequest.getEmail(),
                            registerRequest.getPassword());

            return ResponseEntity.ok("Registration successful");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register user");
        }
    }


    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@RequestBody AuthenticationResponse authenticationResponse) {
        try {
            String token = authenticationResponse.getJwtToken();
            Instant expiration = jwtUtil.getExpirationDateFromToken(token);
            tokenBlacklistService.addToBlacklist(token, expiration);
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unable to logout");
        }
    }


    @PostMapping("/auth/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        User user = accountsService.updateUser(updateUserRequest);
        String jwtToken = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @PostMapping("/auth/updateUserByAdmin")
    public ResponseEntity<?> updateUserByAdmin(@RequestBody UpdateUserRequest updateUserRequest) {
        accountsService.UpdateUserByAdmin(updateUserRequest);
        return ResponseEntity.ok("User updated by admin successfully");
    }


    @PostMapping("/auth/deleteUserByAdmin")
    public ResponseEntity<?> deleteUserByAdmin(@RequestBody UpdateUserRequest updateUserRequest) {
        accountsService.deleteUserByEmail(updateUserRequest.getEmail());
        return ResponseEntity.ok("User deleted by admin successfully");
    }


    private boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

