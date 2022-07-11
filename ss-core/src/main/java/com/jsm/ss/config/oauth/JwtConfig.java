package com.jsm.ss.config.oauth;

import com.jsm.ss.config.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
@Configuration
public class JwtConfig {

    private final Environment env;

    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(env.getProperty("jwt.secret"));
    }
}
