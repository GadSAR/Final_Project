package com.backend.ifm.config;

/*
The AuthenticationRequest class is a simple Java class (also called a POJO or Plain Old Java Object)
 that represents an authentication request, which consists of a username and a password.
This class is used to store the user's credentials when they attempt to log in.
The class uses Lombok annotations for generating boilerplate code such as getters, setters, and constructors.
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// Lombok's annotations for generating getter and setter methods for the class properties
@Getter
@Setter
// Lombok's annotations for generating a no-arguments constructor and an all-arguments constructor
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    // Instance variables for the username and password of the user
    private String username;
    private String password;

}
