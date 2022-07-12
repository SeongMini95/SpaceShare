package com.jsm.ss.domain.member;

import com.jsm.ss.domain.BaseTimeEntity;
import com.jsm.ss.domain.member.converter.SiteDivConverter;
import com.jsm.ss.domain.member.enums.Role;
import com.jsm.ss.domain.member.enums.SiteDiv;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "auth_id", nullable = false)
    private String authId;

    @Convert(converter = SiteDivConverter.class)
    @Column(name = "site_div", nullable = false, length = 10)
    private SiteDiv siteDiv;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "profile")
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 15)
    private Role role;

    @Builder
    public Member(String authId, SiteDiv siteDiv, String email, String nickname, String profile, Role role) {
        this.authId = authId;
        this.siteDiv = siteDiv;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.role = role;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}