package com.jsm.ss.config.oauth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Getter
public class AuthToken {

    private final String token;
    private final Key key;

    public AuthToken(Long id, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, expiry);
    }

    public AuthToken(Long id, String nickname, String profile, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, nickname, profile, expiry);
    }

    private String createAuthToken(Long id, Date expiry) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(UUID.randomUUID().toString())
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(Long id, String nickname, String profile, Date expiry) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(UUID.randomUUID().toString())
                .claim("id", id)
                .claim("nickname", nickname)
                .claim("profile", profile)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ignored) {
        }

        return null;
    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }

        return null;
    }
}
