package com.jsm.ss.config.oauth;

import com.jsm.ss.config.oauth.token.AuthTokenProvider;
import com.jsm.ss.domain.member.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(secret, Role.USER);
    }
}
