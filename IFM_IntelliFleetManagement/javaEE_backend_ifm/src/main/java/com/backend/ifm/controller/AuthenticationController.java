package com.backend.ifm.controller;

import com.backend.ifm.config.AuthenticationRequest;
import com.backend.ifm.config.AuthenticationResponse;
import com.backend.ifm.config.JwtUtil;
import com.backend.ifm.entity.User;
import com.backend.ifm.service.CustomUserDetailsService;
import com.backend.ifm.service.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.backend.ifm.entity.Role;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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
        UserDetails userDetails = null;
        try {
            userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {

            User user = new User();
            user.setName(authenticationRequest.getUsername());
            user.setPassword(authenticationRequest.getPassword());
            user.setRoles(userDetails.getAuthorities().stream().map(authority -> new Role(null, authority.getAuthority(), null)).collect(Collectors.toList()));
            String jwtToken = jwtUtil.generateToken(user);

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            List<Role> roles = authorities.stream()
                    .map(authority -> new Role(null, authority.getAuthority(), null))
                    .collect(Collectors.toList());
            user.setRoles(roles);

            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
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

    private boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

