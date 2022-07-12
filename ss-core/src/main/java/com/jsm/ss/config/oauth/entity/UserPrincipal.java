package com.jsm.ss.config.oauth.entity;

import com.jsm.ss.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserPrincipal implements OAuth2User {

    private Long id;
    private String nickname;
    private String profile;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder
    public UserPrincipal(Long id, String nickname, String profile, Collection<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.nickname = nickname;
        this.profile = profile;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return id.toString();
    }

    public static UserPrincipal create(Member member, Map<String, Object> attributes) {
        return UserPrincipal.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profile(member.getProfile())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(member.getRole().getRole())))
                .attributes(attributes)
                .build();
    }
}
