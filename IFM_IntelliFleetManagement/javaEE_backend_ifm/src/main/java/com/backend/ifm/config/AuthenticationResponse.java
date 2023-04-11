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

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponse {

    private String accessToken;

    public AuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getJwtToken() {
        return this.accessToken;
    }
}
