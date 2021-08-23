package kr.njw.springstudy1.service;

import kr.njw.springstudy1.model.Member;
import kr.njw.springstudy1.repository.impl.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    private final MemoryMemberRepository memberRepository;
    private final MemberService memberService;

    public MemberServiceTest() {
        this.memberRepository = new MemoryMemberRepository();
        this.memberService = new MemberService(this.memberRepository);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        this.memberRepository.clear();
    }

    @Test
    void 회원가입() {
        // given
        MemberService.SignUpRequest request = MemberService.SignUpRequest.builder()
                .name("테스트 이름")
                .email("njw1204@naver.com")
                .password("1234567890")
                .build();

        // when
        Member member = this.memberService.signUp(request);

        // then
        assertThat(member.getId()).isPositive();
        assertThat(this.memberRepository.findById(member.getId()).get()).isEqualTo(member);
    }

    @Test
    void 회원가입_중복이름_오류처리() {
        // given
        MemberService.SignUpRequest request = MemberService.SignUpRequest.builder()
                .name("테스트 이름")
                .email("njw1204@naver.com")
                .password("1234567890")
                .build();
        MemberService.SignUpRequest request2 = MemberService.SignUpRequest.builder()
                .name("테스트 이름2")
                .email("njw1204@naver.com")
                .password("1234567890")
                .build();
        MemberService.SignUpRequest request3 = MemberService.SignUpRequest.builder()
                .name("테스트 이름")
                .email("example@test.com")
                .password("password")
                .build();
        this.memberService.signUp(request);
        this.memberService.signUp(request2);

        // when
        Throwable throwable = catchThrowable(() -> this.memberService.signUp(request3));

        // then
        assertThat(throwable).isInstanceOf(IllegalStateException.class).hasMessageContaining("이미 존재하는 회원입니다");
    }

    @Test
    void 회원_전체_조회() {
        // given
        MemberService.SignUpRequest request = MemberService.SignUpRequest.builder()
                .name("테스트 이름")
                .email("njw1204@naver.com")
                .password("1234567890")
                .build();
        MemberService.SignUpRequest request2 = MemberService.SignUpRequest.builder()
                .name("테스트 이름2")
                .email("njw1204@naver.com")
                .password("1234567890")
                .build();
        Member member = this.memberService.signUp(request);
        Member member2 = this.memberService.signUp(request2);

        // when
        List<Member> members = this.memberService.findMembers();

        // then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.stream()
                .filter(_member -> _member.getName().equals(request.getName()))
                .findAny()
                .get()
        ).isEqualTo(member);
        assertThat(members.stream()
                .filter(_member -> _member.getName().equals(request2.getName()))
                .findAny()
                .get()
        ).isEqualTo(member2);
    }
}
