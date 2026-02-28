package com.yahiaprince.store.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

import com.yahiaprince.store.users.Role;

import java.util.Date;

public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public Jwt(Claims claims, SecretKey secretKey) {
        this.claims = claims;
        this.secretKey = secretKey;
    }

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public Long getUserId() {
        return Long.valueOf(claims.getSubject());
    }

    public Role getRole() {
        var role = claims.get("role", String.class);
        if (role == null)
            return null; 
        return Role.valueOf(role);
    }

    public String toString() {
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}