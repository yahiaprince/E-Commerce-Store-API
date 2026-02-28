package com.yahiaprince.store.auth;

import java.util.Date;
import org.springframework.stereotype.Service;

import com.yahiaprince.store.users.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    private Jwt generateToken(User user, final long tokenExpiration) {
        var claims = Jwts.claims().subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();
        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    public Jwt parseToken(String token) {
        var claims = getClaims(token);
        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}