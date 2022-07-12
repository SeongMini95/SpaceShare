package com.jsm.ss.service;

import com.jsm.ss.domain.membercertify.MemberCertify;
import com.jsm.ss.domain.membercertify.enums.CertifyCode;
import com.jsm.ss.domain.membercertify.repository.MemberCertifyRepository;
import com.jsm.ss.exception.CertifyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCertifyService {

    private final MemberCertifyRepository memberCertifyRepository;

    @Transactional(readOnly = true)
    public MemberCertify find(String certifyKey, CertifyCode certifyCode) {
        return memberCertifyRepository.findByCertifyKeyAndCertifyCode(certifyKey, certifyCode).orElseThrow(CertifyNotFoundException::new);
    }
}
