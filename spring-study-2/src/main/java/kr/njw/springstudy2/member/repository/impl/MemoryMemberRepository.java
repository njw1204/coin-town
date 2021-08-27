package kr.njw.springstudy2.member.repository.impl;

import kr.njw.springstudy2.member.model.Member;
import kr.njw.springstudy2.member.repository.MemberRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository implements MemberRepository {
    private static final Map<Long, Member> store = new ConcurrentHashMap<>();

    @Override
    public void save(Member member) {
        MemoryMemberRepository.store.put(member.getId(), member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
