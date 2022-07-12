package com.jsm.ss.controller;

import com.jsm.ss.config.oauth.token.AuthToken;
import com.jsm.ss.config.oauth.token.AuthTokenProvider;
import com.jsm.ss.domain.memberrefreshtoken.MemberRefreshToken;
import com.jsm.ss.domain.memberrefreshtoken.repository.MemberRefreshTokenRepository;
import com.jsm.ss.dto.response.auth.TokenResponseDto;
import com.jsm.ss.util.CookieUtil;
import com.jsm.ss.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthTokenProvider tokenProvider;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;

    private final static String REFRESH_TOKEN = "refresh_token";
    private final static long TREE_DAYS_MS = 259200000;

    @Value("${jwt.expiry}")
    private long expiry;

    @Value("${jwt.refreshTokenExpiry}")
    private long refreshTokenExpiry;

    @GetMapping("/refresh")
    public TokenResponseDto refresh(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return new TokenResponseDto(false, null);
        }

        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return new TokenResponseDto(false, null);
        }

        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN).map(Cookie::getValue).orElse(null);
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);
        if (!authRefreshToken.validate()) {
            return new TokenResponseDto(false, null);
        }

        Long memberId = claims.get("id", Long.class);
        String nickname = claims.get("nickname", String.class);
        String profile = claims.get("profile", String.class);

        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByMemberIdAndRefreshToken(memberId, refreshToken).orElse(null);
        if (memberRefreshToken == null) {
            return new TokenResponseDto(false, null);
        }

        long now = System.currentTimeMillis();
        AuthToken newAccessToken = tokenProvider.createAuthToken(memberId, nickname, profile, new Date(now + expiry));

        long remainTime = refreshTokenExpiry = now;
        if (remainTime <= TREE_DAYS_MS) {
            authRefreshToken = tokenProvider.createAuthToken(memberId, new Date(now + refreshTokenExpiry));
            memberRefreshToken.updateRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 1000;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return new TokenResponseDto(true, newAccessToken.getToken());
    }
}
