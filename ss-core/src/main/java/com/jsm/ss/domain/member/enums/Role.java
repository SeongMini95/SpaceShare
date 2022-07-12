package com.jsm.ss.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    USER("일반 사용자", "ROLE_USER"),
    HOST("호스트", "ROLE_HOST"),
    ADMIN("관리자", "ROLE_ADMIN");

    private final String desc;
    private final String role;
}
