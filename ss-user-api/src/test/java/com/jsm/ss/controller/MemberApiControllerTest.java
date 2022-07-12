package com.jsm.ss.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsm.ss.domain.member.Member;
import com.jsm.ss.domain.member.enums.Role;
import com.jsm.ss.domain.member.enums.SiteDiv;
import com.jsm.ss.domain.member.repository.MemberRepository;
import com.jsm.ss.domain.membercertify.MemberCertify;
import com.jsm.ss.domain.membercertify.enums.CertifyCode;
import com.jsm.ss.domain.membercertify.repository.MemberCertifyRepository;
import com.jsm.ss.dto.request.member.AfterSignUpNicknameSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MemberApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberCertifyRepository memberCertifyRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @AfterEach
    void tearDown() {
        memberCertifyRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입 후 닉네임 설정")
    void afterSignUp() throws Exception {
        // given
        Member mockMember = memberRepository.save(Member.builder()
                .authId("naverAuthId")
                .siteDiv(SiteDiv.NAVER)
                .email("test123@naver.com")
                .role(Role.USER)
                .build());

        MemberCertify mockMemberCertify = memberCertifyRepository.save(new MemberCertify(mockMember, CertifyCode.SET_ADD_INFO, 0L));

        AfterSignUpNicknameSaveRequestDto requestDto = AfterSignUpNicknameSaveRequestDto.builder()
                .code(mockMemberCertify.getCertifyKey())
                .nickname("test123")
                .build();

        // when
        ResultActions actions = mvc.perform(post("/api/member/afterSignUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto)));

        Member member = memberRepository.findAll().get(0);
        MemberCertify memberCertify = memberCertifyRepository.findAll().get(0);

        // then
        actions
                .andExpect(status().isOk());

        assertThat(member.getNickname()).isEqualTo(requestDto.getNickname());
        assertThat(memberCertify.isUse()).isTrue();
    }
}