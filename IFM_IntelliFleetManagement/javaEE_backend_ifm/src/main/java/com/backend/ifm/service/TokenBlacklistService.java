package com.backend.ifm.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;



@Service
public class TokenBlacklistService {

    private ConcurrentHashMap<String, Instant> blacklist = new ConcurrentHashMap<>();

    public void addToBlacklist(String token, Instant expiration) {
        blacklist.put(token, expiration);
    }

    public boolean isBlacklisted(String token) {
        Instant expiration = blacklist.get(token);

        if (expiration == null) {
            return false;
        }

        if (Instant.now().isAfter(expiration)) {
            blacklist.remove(token);
            return false;
        }

        return true;
    }
}
