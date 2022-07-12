package com.jsm.ss.config.oauth.info;

import com.jsm.ss.config.oauth.info.impl.KakaoOAuth2UserInfo;
import com.jsm.ss.config.oauth.info.impl.NaverOAuth2UserInfo;
import com.jsm.ss.domain.member.enums.SiteDiv;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(SiteDiv siteDiv, Map<String, Object> attributes) {
        switch (siteDiv) {
            case NAVER:
                return new NaverOAuth2UserInfo(attributes);
            case KAKAO:
                return new KakaoOAuth2UserInfo(attributes);
            default:
                throw new IllegalArgumentException("SiteDiv가 존재하지 않습니다.");
        }
    }
}
