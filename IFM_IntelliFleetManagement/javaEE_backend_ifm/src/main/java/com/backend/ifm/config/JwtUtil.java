package com.backend.ifm.config;
import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.backend.ifm.entity.Role;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "ifm-is-the-best";

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getName());
        claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        claims.put("companies", user.getCompanies().stream().map(Company::getName).collect(Collectors.toList()));
        claims.put("email", user.getEmail());

        long TOKEN_VALIDITY = 1000 * 60 * 60 * 10;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Instant getExpirationDateFromToken(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.toInstant();
    }

    public boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return username.equals(user.getName()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
