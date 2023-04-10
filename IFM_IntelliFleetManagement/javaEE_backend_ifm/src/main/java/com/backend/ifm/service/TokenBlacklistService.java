package com.backend.ifm.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;


/*
This class is a service to manage the JWT token blacklist.
It is annotated with @Service, indicating that it is a Spring service class.

A ConcurrentHashMap named blacklist is used to store blacklisted tokens and their expiration dates. This allows for thread-safe access and manipulation of the blacklist.

The addToBlacklist method takes a token and its expiration date as arguments and adds them to the blacklist map.

The isBlacklisted method checks if a given token is blacklisted.
It retrieves the token's expiration date from the blacklist map.
If the token is not found in the map, the method returns false, indicating that the token is not blacklisted.
If the token's expiration date has passed, the method removes the token from the blacklist and returns false,
 indicating that the token is no longer blacklisted.
If the token is found in the blacklist and its expiration date has not passed,
 the method returns true, indicating that the token is blacklisted.
 */
// Service class to manage the JWT token blacklist
@Service
public class TokenBlacklistService {

    // A concurrent hash map to store blacklisted tokens and their expiration dates
    private ConcurrentHashMap<String, Instant> blacklist = new ConcurrentHashMap<>();

    // Method to add a token to the blacklist along with its expiration date
    public void addToBlacklist(String token, Instant expiration) {
        blacklist.put(token, expiration);
    }

    // Method to check if a token is blacklisted
    public boolean isBlacklisted(String token) {
        // Get the token's expiration date from the blacklist
        Instant expiration = blacklist.get(token);

        // If the token is not found in the blacklist, return false (not blacklisted)
        if (expiration == null) {
            return false;
        }

        // If the token's expiration date has passed, remove it from the blacklist and return false (not blacklisted)
        if (Instant.now().isAfter(expiration)) {
            blacklist.remove(token);
            return false;
        }

        // If the token is found and not expired, return true (blacklisted)
        return true;
    }
}
