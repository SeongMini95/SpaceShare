package com.jsm.ss.config.oauth.handler;

import com.jsm.ss.config.oauth.entity.UserPrincipal;
import com.jsm.ss.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.jsm.ss.config.oauth.token.AuthToken;
import com.jsm.ss.config.oauth.token.AuthTokenProvider;
import com.jsm.ss.domain.member.Member;
import com.jsm.ss.domain.member.repository.MemberRepository;
import com.jsm.ss.domain.membercertify.enums.CertifyCode;
import com.jsm.ss.domain.membercertify.repository.MemberCertifyRepository;
import com.jsm.ss.domain.memberrefreshtoken.MemberRefreshToken;
import com.jsm.ss.domain.memberrefreshtoken.repository.MemberRefreshTokenRepository;
import com.jsm.ss.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;
    private final AuthTokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final MemberCertifyRepository memberCertifyRepository;

    private static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final String REFRESH_TOKEN = "refresh_token";

    @Value("${jwt.expiry}")
    private long expiry;

    @Value("${jwt.refreshTokenExpiry}")
    private long refreshTokenExpiry;

    @Value("${jwt.host}")
    private String host;

    @Value("${jwt.redirectUri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue).orElse(getDefaultTargetUrl());

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        long now = System.currentTimeMillis();
        AuthToken accessToken = tokenProvider.createAuthToken(user.getId(), user.getNickname(), user.getProfile(), new Date(now + expiry));
        AuthToken refreshToken = tokenProvider.createAuthToken(user.getId(), new Date(now + refreshTokenExpiry));

        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByMemberId(user.getId()).orElse(null);
        if (memberRefreshToken != null) {
            memberRefreshToken.updateRefreshToken(refreshToken.getToken());
        } else {
            Member member = memberRepository.findById(user.getId()).orElseThrow();
            memberRefreshTokenRepository.save(MemberRefreshToken.builder()
                    .member(member)
                    .refreshToken(refreshToken.getToken())
                    .build());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 1000;

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(host + redirectUri);
        Member member = memberRepository.findById(user.getId()).orElseThrow();
        String nickname = member.getNickname();

        if (nickname == null || nickname.isBlank()) {
            String certifyKey = memberCertifyRepository.findCertifyKeyByMemberIdAndCertifyCode(member.getId(), CertifyCode.SET_ADD_INFO);
            uriComponentsBuilder.queryParam("code", certifyKey);
        } else {
            uriComponentsBuilder
                    .queryParam("token", accessToken.getToken())
                    .queryParam("redirect_uri", targetUri);
        }

        return uriComponentsBuilder.build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        oAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
