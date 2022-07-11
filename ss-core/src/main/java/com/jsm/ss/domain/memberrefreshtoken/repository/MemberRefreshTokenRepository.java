package com.jsm.ss.domain.memberrefreshtoken.repository;

import com.jsm.ss.domain.memberrefreshtoken.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
}