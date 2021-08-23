package kr.njw.springstudy1.repository.impl;

import kr.njw.springstudy1.model.Member;
import kr.njw.springstudy1.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    
    private static final Map<Long, Member> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Member save(Member member) {
        member.setId(MemoryMemberRepository.sequence.incrementAndGet());
        MemoryMemberRepository.store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = MemoryMemberRepository.store.get(id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return MemoryMemberRepository.store.values()
                .stream()
                .filter(member -> member.getName().equalsIgnoreCase(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(MemoryMemberRepository.store.values());
    }

    public void clear() {
        MemoryMemberRepository.store.clear();
    }
}
