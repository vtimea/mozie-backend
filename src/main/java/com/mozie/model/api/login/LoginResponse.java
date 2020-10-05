package com.mozie.model.api.login;

import org.joda.time.LocalDateTime;

public class LoginResponse {
    private String token;
    private String expiration;

    public LoginResponse(String token, LocalDateTime expiration) {
        this.token = token;
        this.expiration = expiration.toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
