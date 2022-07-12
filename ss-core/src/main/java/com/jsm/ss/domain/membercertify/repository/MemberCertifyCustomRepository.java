package com.jsm.ss.domain.membercertify.repository;

import com.jsm.ss.domain.membercertify.enums.CertifyCode;

public interface MemberCertifyCustomRepository {

    String findCertifyKeyByMemberIdAndCertifyCode(Long memberId, CertifyCode certifyCode);
}
