package kr.njw.springstudy2.member.service;

import kr.njw.springstudy2.config.AppConfig;
import kr.njw.springstudy2.member.constant.Grade;
import kr.njw.springstudy2.member.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest {
    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        this.memberService = appConfig.memberService();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member(1L, "이름A", Grade.VIP);

        // when
        this.memberService.join(member);
        Member foundMember = this.memberService.findMember(member.getId()).orElseThrow();

        // then
        assertThat(foundMember).isEqualTo(member);
    }
}
