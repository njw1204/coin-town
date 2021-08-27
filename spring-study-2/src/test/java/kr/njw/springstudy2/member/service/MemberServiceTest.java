package kr.njw.springstudy2.member.service;

import kr.njw.springstudy2.member.constant.Grade;
import kr.njw.springstudy2.member.model.Member;
import kr.njw.springstudy2.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest {
    MemberService memberService = new MemberServiceImpl();

    @Test
    void 회원가입() {
        // given
        Member member = new Member(1L, "이름A", Grade.VIP);

        // when
        this.memberService.join(member);
        Member foundMember = this.memberService.findMember(member.getId()).get();

        // then
        assertThat(foundMember).isEqualTo(member);
    }
}
