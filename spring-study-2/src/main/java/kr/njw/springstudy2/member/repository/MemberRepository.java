package kr.njw.springstudy2.member.repository;

import kr.njw.springstudy2.member.model.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(Long id);
}
