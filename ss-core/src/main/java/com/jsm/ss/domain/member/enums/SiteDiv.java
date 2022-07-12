package com.jsm.ss.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum SiteDiv {

    NAVER("네이버", "1"),
    KAKAO("카카오", "2");

    private final String desc;
    private final String code;

    public static SiteDiv ofCode(String code) {
        return Arrays.stream(SiteDiv.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("SiteDiv, code=[%s]가 존재하지 않습니다.", code)));
    }
}
