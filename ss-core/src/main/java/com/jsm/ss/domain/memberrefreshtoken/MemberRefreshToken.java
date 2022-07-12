package com.jsm.ss.domain.memberrefreshtoken;

import com.jsm.ss.domain.BaseTimeEntity;
import com.jsm.ss.domain.member.Member;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@Table(name = "member_refresh_token")
public class MemberRefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "refresh_token", nullable = false, length = 256)
    private String refreshToken;

    @Builder
    public MemberRefreshToken(Member member, String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }
}