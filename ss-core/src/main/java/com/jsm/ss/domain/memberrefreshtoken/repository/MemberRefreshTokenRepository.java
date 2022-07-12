package com.jsm.ss.domain.memberrefreshtoken.repository;

import com.jsm.ss.domain.memberrefreshtoken.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

    Optional<MemberRefreshToken> findByMemberId(Long memberId);

    Optional<MemberRefreshToken> findByMemberIdAndRefreshToken(Long memberId, String refreshToken);
}