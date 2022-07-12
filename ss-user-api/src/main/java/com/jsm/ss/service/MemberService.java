package com.jsm.ss.service;

import com.jsm.ss.config.oauth.token.AuthToken;
import com.jsm.ss.config.oauth.token.AuthTokenProvider;
import com.jsm.ss.domain.member.Member;
import com.jsm.ss.domain.membercertify.MemberCertify;
import com.jsm.ss.domain.membercertify.enums.CertifyCode;
import com.jsm.ss.dto.request.member.AfterSignUpNicknameSaveRequestDto;
import com.jsm.ss.dto.response.auth.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberCertifyService memberCertifyService;
    private final AuthTokenProvider tokenProvider;

    @Value("${jwt.expiry}")
    private long expiry;

    @Transactional
    public TokenResponseDto afterSignUp(AfterSignUpNicknameSaveRequestDto requestDto) {
        MemberCertify memberCertify = memberCertifyService.find(requestDto.getCode(), CertifyCode.SET_ADD_INFO);
        memberCertify.use();

        Member member = memberCertify.getMember();
        member.updateNickname(requestDto.getNickname());

        long now = System.currentTimeMillis();
        AuthToken accessToken = tokenProvider.createAuthToken(member.getId(), member.getNickname(), member.getProfile(), new Date(now + expiry));

        return new TokenResponseDto(true, accessToken.getToken());
    }
}
