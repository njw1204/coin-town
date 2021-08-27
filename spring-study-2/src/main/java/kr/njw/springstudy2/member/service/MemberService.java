package kr.njw.springstudy2.member.service;

import kr.njw.springstudy2.member.model.Member;

import java.util.Optional;

public interface MemberService {
    void join(Member member);
    Optional<Member> findMember(Long memberId);
}
