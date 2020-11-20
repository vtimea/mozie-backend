package com.mozie.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import static com.mozie.utils.ApiKeys.JwtSigningKey;

public class AuthToken {
    private static final int TOKEN_VALIDITY_DAYS = 90;

    private final String jwt;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    private AuthToken(String jwt, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.jwt = jwt;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public static AuthToken generateToken(String userId) {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime expires = created.plusDays(TOKEN_VALIDITY_DAYS);
        String jwt = Jwts.builder().setIssuer("Mozie web service")
                .setSubject(userId)
                .setExpiration(new Date(expires.toEpochSecond(ZoneOffset.UTC)))
                .signWith(Keys.hmacShaKeyFor(JwtSigningKey))
                .compact();
        return new AuthToken(jwt, created, expires);
    }

    public static boolean isValid(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(JwtSigningKey).build().parseClaimsJws(jwtToken);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    public static boolean isExpired(String jwtToken) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(JwtSigningKey).build().parseClaimsJws(jwtToken);
        } catch (JwtException e) {
            return true;
        }
        LocalDateTime exp = LocalDateTime.ofInstant(claims.getBody().getExpiration().toInstant(), ZoneId.systemDefault());
        return exp.isBefore(LocalDateTime.now());
    }

    public static String getUserId(String jwt) {
        if (!isValid(jwt)) {
            return null;
        }
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(JwtSigningKey).build().parseClaimsJws(jwt);
        } catch (JwtException e) {
            return null;
        }
        return claims.getBody().getSubject();
    }

    public String getJwt() {
        return jwt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
