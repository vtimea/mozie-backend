package com.mozie.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import static com.mozie.utils.ApiKeys.JwtSigningKey;

public class AuthToken {
    private final String jwt;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    private AuthToken(String jwt, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.jwt = jwt;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public static AuthToken generateToken(String userId, int validity) {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime expires = new LocalDateTime(created.plusMillis(validity));
        String jwt = Jwts.builder().setIssuer("Mozie web service")
                .setSubject(userId)
                .setExpiration(expires.toDate())
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
        DateTime exp = new DateTime(claims.getBody().getExpiration());
        return exp.isBeforeNow();
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
