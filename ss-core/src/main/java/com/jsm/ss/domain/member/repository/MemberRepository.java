package com.jsm.ss.domain.member.repository;

import com.jsm.ss.domain.member.Member;
import com.jsm.ss.domain.member.enums.Role;
import com.jsm.ss.domain.member.enums.SiteDiv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByAuthIdAndSiteDivAndRole(String authId, SiteDiv siteDiv, Role role);
}