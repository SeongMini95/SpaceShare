package com.jsm.ss.config;

import com.jsm.ss.config.oauth.filter.TokenAuthenticationFilter;
import com.jsm.ss.config.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.jsm.ss.config.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.jsm.ss.config.oauth.handler.RestAuthenticationEntryPoint;
import com.jsm.ss.config.oauth.handler.TokenAccessDeniedHandler;
import com.jsm.ss.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.jsm.ss.config.oauth.service.CustomOAuth2UserService;
import com.jsm.ss.config.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final TokenAccessDeniedHandler accessDeniedHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;
    private final AuthTokenProvider tokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers("/api/member/afterSignUp").permitAll()
                .anyRequest().authenticated()
                .and()

                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
                .and()

                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()

                .successHandler(successHandler)
                .failureHandler(failureHandler);

        http.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
