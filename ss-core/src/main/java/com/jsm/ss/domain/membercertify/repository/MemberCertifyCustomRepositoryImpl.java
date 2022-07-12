package com.jsm.ss.domain.membercertify.repository;

import com.jsm.ss.domain.membercertify.MemberCertify;
import com.jsm.ss.domain.membercertify.enums.CertifyCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.jsm.ss.domain.membercertify.QMemberCertify.memberCertify;

@RequiredArgsConstructor
public class MemberCertifyCustomRepositoryImpl implements MemberCertifyCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public String findCertifyKeyByMemberIdAndCertifyCode(Long memberId, CertifyCode certifyCode) {
        LocalDateTime now = LocalDateTime.now();

        return factory
                .select(memberCertify.certifyKey)
                .from(memberCertify)
                .where(memberCertify.member.id.eq(memberId),
                        memberCertify.certifyCode.eq(certifyCode),
                        memberCertify.isUse.eq(false),
                        memberCertify.createDatetime.before(now),
                        eqCertifyCode(certifyCode, now))
                .orderBy(memberCertify.id.desc())
                .fetchFirst();
    }

    @Override
    public Optional<MemberCertify> findByCertifyKeyAndCertifyCode(String certifyKey, CertifyCode certifyCode) {
        LocalDateTime now = LocalDateTime.now();

        return Optional.ofNullable(factory
                .selectFrom(memberCertify)
                .where(memberCertify.certifyKey.eq(certifyKey),
                        memberCertify.certifyCode.eq(certifyCode),
                        memberCertify.isUse.eq(false),
                        memberCertify.createDatetime.before(now),
                        eqCertifyCode(certifyCode, now))
                .orderBy(memberCertify.id.desc())
                .fetchFirst());
    }

    private BooleanExpression eqCertifyCode(CertifyCode certifyCode, LocalDateTime now) {
        if (certifyCode == CertifyCode.SET_ADD_INFO) {
            return null;
        }

        return memberCertify.expireTime.after(now);
    }
}
