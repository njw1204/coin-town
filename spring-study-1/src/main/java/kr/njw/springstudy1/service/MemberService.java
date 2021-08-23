package kr.njw.springstudy1.service;

import kr.njw.springstudy1.model.Member;
import kr.njw.springstudy1.repository.MemberRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member signUp(SignUpRequest request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .build();

        this.memberRepository.findByName(member.getName()).ifPresent(sameMember -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });

        return this.memberRepository.save(member);
    }

    public List<Member> findMembers() {
        return this.memberRepository.findAll();
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest {
        @Builder.Default private String email = "";
        @Builder.Default private String password = "";
        @Builder.Default private String name = "";
    }
}
