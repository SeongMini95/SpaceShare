package com.jsm.ss.domain.membercertify;

import com.jsm.ss.domain.BaseTimeEntity;
import com.jsm.ss.domain.member.Member;
import com.jsm.ss.domain.membercertify.converter.CertifyCodeConverter;
import com.jsm.ss.domain.membercertify.enums.CertifyCode;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@Table(name = "member_certify")
public class MemberCertify extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Convert(converter = CertifyCodeConverter.class)
    @Column(name = "certify_code", nullable = false)
    private CertifyCode certifyCode;

    @Column(name = "certify_key", nullable = false, length = 64)
    private String certifyKey;

    @Column(name = "is_use", nullable = false)
    private boolean isUse;

    @Column(name = "expire_time", nullable = false)
    private LocalDateTime expireTime;

    @Builder
    public MemberCertify(Member member, CertifyCode certifyCode, String certifyKey, boolean isUse, LocalDateTime expireTime) {
        this.member = member;
        this.certifyCode = certifyCode;
        this.certifyKey = certifyKey;
        this.isUse = isUse;
        this.expireTime = expireTime;
    }

    public MemberCertify(Member member, CertifyCode certifyCode, Long minutes) {
        this.member = member;
        this.certifyCode = certifyCode;
        this.certifyKey = RandomStringUtils.randomAlphanumeric(64);
        this.isUse = false;
        this.expireTime = LocalDateTime.now().plusMinutes(minutes);
    }
}