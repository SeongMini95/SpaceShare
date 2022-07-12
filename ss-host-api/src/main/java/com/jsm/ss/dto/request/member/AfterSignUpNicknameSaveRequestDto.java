package com.jsm.ss.dto.request.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AfterSignUpNicknameSaveRequestDto {

    @Length(min = 64, max = 64,
            message = "잘못된 요청입니다.")
    private String code;

    @Pattern(regexp = "^[A-Za-z0-9가-힣]{2,15}$",
            message = "닉네임을 확인하세요. (한글, 영어, 숫자 2~15자리 이내")
    private String nickname;

    @Builder
    public AfterSignUpNicknameSaveRequestDto(String code, String nickname) {
        this.code = code;
        this.nickname = nickname;
    }
}
