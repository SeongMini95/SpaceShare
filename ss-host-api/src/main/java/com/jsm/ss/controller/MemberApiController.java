package com.jsm.ss.controller;

import com.jsm.ss.dto.request.member.AfterSignUpNicknameSaveRequestDto;
import com.jsm.ss.dto.response.auth.TokenResponseDto;
import com.jsm.ss.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/afterSignUp")
    public TokenResponseDto afterSignUp(@Valid @RequestBody AfterSignUpNicknameSaveRequestDto requestDto) {
        return memberService.afterSignUp(requestDto);
    }
}
