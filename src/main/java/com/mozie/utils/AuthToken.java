package com.mozie.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;

import static com.mozie.utils.ApiKeys.JwtSigningKey;

public class AuthToken {
    private final String jwt;
    private final DateTime createdAt;
    private final DateTime expiresAt;

    private AuthToken(String jwt, DateTime createdAt, DateTime expiresAt) {
        this.jwt = jwt;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public static AuthToken generateToken(String userId, Long validity) {
        DateTime created = DateTime.now();
        DateTime expires = new DateTime(created.getMillis() + (validity));
        String jwt = Jwts.builder().setIssuer("Mozie web service")
                .setSubject(userId)
                .setExpiration(expires.toDate())
                .signWith(Keys.hmacShaKeyFor(JwtSigningKey))
                .compact();
        return new AuthToken(jwt, created, expires);
    }

    public static boolean isValid(String jwtToken) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(JwtSigningKey).build().parseClaimsJws(jwtToken);
        } catch (JwtException e) {
            return false;
        }
        DateTime exp = new DateTime(claims.getBody().getExpiration());
        return exp.isAfterNow();
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

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getExpiresAt() {
        return expiresAt;
    }
}
