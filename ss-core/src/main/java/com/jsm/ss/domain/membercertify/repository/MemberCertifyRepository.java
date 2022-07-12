package com.jsm.ss.domain.membercertify.repository;

import com.jsm.ss.domain.membercertify.MemberCertify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCertifyRepository extends JpaRepository<MemberCertify, Long>, MemberCertifyCustomRepository {
}