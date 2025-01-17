package com.mozie.model.database;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "userId")
    @NonNull
    private String userId;

    @Column(name = "token")
    private String token;

    @Column(name = "expires")
    private LocalDateTime expires;

    public User() {
    }

    public User(String userId, String token, LocalDateTime expires) {
        this.userId = userId;
        this.token = token;
        this.expires = expires;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }
}