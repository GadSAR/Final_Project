package com.backend.ifm.config;

/*
The AuthenticationResponse class is a simple Java class (a POJO or Plain Old Java Object)
 that represents an authentication response, which consists of an access token (JWT token).
This class is used to store the access token returned after successful authentication.
The class uses Lombok annotations for generating boilerplate code such as getters, setters,
 and constructors. Here's an explanation and comments for the class:
 */
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok's annotations for generating getter and setter methods for the class properties
@Getter
@Setter
// Lombok's annotation for generating a no-arguments constructor
@NoArgsConstructor
public class AuthenticationResponse {

    // Instance variable for the access token (JWT token) of the user
    private String accessToken;

    // Constructor with an argument for the access token
    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // Getter method for the JWT token, which is the same as the access token
    public String getJwtToken() {
        return this.accessToken;
    }
}
