package com.jsm.ss.config.oauth.service;

import com.jsm.ss.config.oauth.entity.UserPrincipal;
import com.jsm.ss.config.oauth.info.OAuth2UserInfo;
import com.jsm.ss.config.oauth.info.OAuth2UserInfoFactory;
import com.jsm.ss.domain.member.Member;
import com.jsm.ss.domain.member.enums.Role;
import com.jsm.ss.domain.member.enums.SiteDiv;
import com.jsm.ss.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        return this.process(userRequest, user);
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SiteDiv siteDiv = SiteDiv.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(siteDiv, user.getAttributes());
        Member savedMember = memberRepository.findByAuthIdAndSiteDivAndRole(userInfo.getId(), siteDiv, Role.USER)
                .orElse(Member.builder()
                        .authId(userInfo.getId())
                        .email(userInfo.getEmail())
                        .siteDiv(siteDiv)
                        .role(Role.USER)
                        .build());

        if (savedMember.getId() != null) {
            savedMember.updateEmail(userInfo.getEmail());
        } else {
            savedMember = memberRepository.save(savedMember);
        }

        return UserPrincipal.create(savedMember, user.getAttributes());
    }
}
