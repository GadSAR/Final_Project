package com.example.demo.config;
import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.example.demo.entity.Role;

/*
The JwtUtil class is a Spring component responsible for generating, extracting, and validating JWT tokens.
@Component: A Spring annotation that marks this class as a Spring component to be managed by the Spring container.
SECRET_KEY: A secret key used for signing JWT tokens.
TOKEN_VALIDITY: The duration for which the JWT token is valid, in this case, 10 hours.
generateToken(): A method that generates a JWT token for a user, including the user's roles in the claims.
getExpirationDateFromToken(): A method that extracts the expiration date from a JWT token and returns it as an Instant.
validateToken(): A method that validates a JWT token for a user by checking the username and whether the token is expired.
extractUsername(): A method that extracts the username (subject) from a JWT token.
extractClaim(): A generic method that extracts a claim from a JWT token using a claims resolver function.
extractAllClaims(): A method that extracts all claims from a JWT token.
isTokenExpired(): A method that checks if a JWT token is expired.
`extractExpiration()

 */
@Component
public class JwtUtil {
    // Secret key for signing JWT tokens
    private String SECRET_KEY = "your-secret-key";
    // Token validity duration in milliseconds (10 hours)
    private long TOKEN_VALIDITY = 1000 * 60 * 60 * 10;

    // Generate a JWT token for a user
    public String generateToken(User user) {
        // Create claims and set the subject (username)
        Claims claims = Jwts.claims().setSubject(user.getName());
        // Add the user's roles to the claims
        claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        // Build and sign the JWT token
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Get the expiration date of the token as an Instant
    public Instant getExpirationDateFromToken(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.toInstant();
    }

    // Validate the JWT token for a user
    public boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return username.equals(user.getName()) && !isTokenExpired(token);
    }

    // Extract the username (subject) from the JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a claim from the JWT token using a claims resolver function
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Check if the JWT token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract the expiration date from the JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
