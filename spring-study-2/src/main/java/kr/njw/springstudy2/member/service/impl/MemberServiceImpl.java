package kr.njw.springstudy2.member.service.impl;

import kr.njw.springstudy2.member.model.Member;
import kr.njw.springstudy2.member.repository.MemberRepository;
import kr.njw.springstudy2.member.repository.impl.MemoryMemberRepository;
import kr.njw.springstudy2.member.service.MemberService;

import java.util.Optional;

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        this.memberRepository.save(member);
    }

    @Override
    public Optional<Member> findMember(Long memberId) {
        return this.memberRepository.findById(memberId);
    }
}
