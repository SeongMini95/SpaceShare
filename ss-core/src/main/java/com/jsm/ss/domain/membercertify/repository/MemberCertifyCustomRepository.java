package com.jsm.ss.domain.membercertify.repository;

import com.jsm.ss.domain.membercertify.MemberCertify;
import com.jsm.ss.domain.membercertify.enums.CertifyCode;

import java.util.Optional;

public interface MemberCertifyCustomRepository {

    String findCertifyKeyByMemberIdAndCertifyCode(Long memberId, CertifyCode certifyCode);

    Optional<MemberCertify> findByCertifyKeyAndCertifyCode(String certifyKey, CertifyCode certifyCode);
}
