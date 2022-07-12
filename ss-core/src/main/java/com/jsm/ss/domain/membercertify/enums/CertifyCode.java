package com.jsm.ss.domain.membercertify.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CertifyCode {

    SET_ADD_INFO("회원가입 후 정보 설정", "1");

    private final String desc;
    private final String code;

    public static CertifyCode ofCode(String code) {
        return Arrays.stream(CertifyCode.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("CertifyCode, code=[%s]가 존재하지 않습니다.", code)));
    }
}
