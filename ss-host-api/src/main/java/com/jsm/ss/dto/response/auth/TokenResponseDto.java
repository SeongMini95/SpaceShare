package com.jsm.ss.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponseDto {

    private boolean result;
    private String token;
}
