package com.jsm.ss.config.oauth.token;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class AuthTokenProvider {

    private final Key key;

    public AuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(Long id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(Long id, String nickname, String profile, Date expiry) {
        return new AuthToken(id, nickname, profile, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }
}
