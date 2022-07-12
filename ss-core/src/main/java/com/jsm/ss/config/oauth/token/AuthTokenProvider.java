package com.jsm.ss.config.oauth.token;

import com.jsm.ss.domain.member.enums.Role;
import com.jsm.ss.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class AuthTokenProvider {

    private final Key key;
    private final Role role;

    public AuthTokenProvider(String secret, Role role) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.role = role;
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

    public Authentication getAuthentication(AuthToken authToken) {
        if (authToken.validate()) {
            Claims claims = authToken.getTokenClaims();
            Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(role.getRole()));
            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}
