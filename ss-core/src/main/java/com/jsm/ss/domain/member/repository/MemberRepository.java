package com.jsm.ss.domain.member.repository;

import com.jsm.ss.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}