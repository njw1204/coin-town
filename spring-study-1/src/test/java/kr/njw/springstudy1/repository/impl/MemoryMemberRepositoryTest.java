package kr.njw.springstudy1.repository.impl;

import kr.njw.springstudy1.model.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    private final MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        memoryMemberRepository.clear();
    }

    @Test
    void 회원_저장() {
        // given
        Member member = Member.builder()
                .name("이름")
                .email("njw1204@naver.com")
                .password("password")
                .build();
        Member member2 = Member.builder()
                .name("이름2")
                .email("njw1204@cau.ac.kr")
                .password("pw")
                .build();

        // when
        Member saveResult = this.memoryMemberRepository.save(member);
        Member saveResult2 = this.memoryMemberRepository.save(member2);

        // then
        assertThat(saveResult).isEqualTo(member);
        assertThat(saveResult).isEqualTo(this.memoryMemberRepository.findById(member.getId()).get());
        assertThat(saveResult2).isEqualTo(member2);
        assertThat(saveResult2).isEqualTo(this.memoryMemberRepository.findById(member2.getId()).get());
        assertThat(member2.getId()).isEqualTo(member.getId() + 1);
    }

    @Test
    void 회원_ID로_조회() {
        // given
        Member member = Member.builder()
                .name("name1")
                .email("email@test.com")
                .password("test")
                .build();
        Member member2 = Member.builder()
                .name("name2")
                .email("email2@test.com")
                .password("test2")
                .build();
        this.memoryMemberRepository.save(member);
        this.memoryMemberRepository.save(member2);

        // when
        Member foundMember = this.memoryMemberRepository.findById(member.getId()).get();
        Member foundMember2 = this.memoryMemberRepository.findById(member2.getId()).get();
        Optional<Member> foundMember3 = this.memoryMemberRepository.findById(member.getId() + member2.getId());

        // then
        assertThat(foundMember).isEqualTo(member);
        assertThat(foundMember2).isEqualTo(member2);
        assertThat(foundMember3.isPresent()).isEqualTo(false);
    }

    @Test
    void 회원_이름으로_조회() {
        // given
        Member member = Member.builder()
                .name("이름으로 찾기")
                .email("email@test.com")
                .password("test")
                .build();
        this.memoryMemberRepository.save(member);

        // when
        Member foundMember = this.memoryMemberRepository.findByName(member.getName()).get();
        Optional<Member> foundMember2 = this.memoryMemberRepository.findByName(member.getName() + " ");
        Optional<Member> foundMember3 = this.memoryMemberRepository.findByName("");
        this.memoryMemberRepository.clear();
        Optional<Member> foundMember4 = this.memoryMemberRepository.findByName(member.getName());

        // then
        assertThat(foundMember).isEqualTo(member);
        assertThat(foundMember2.isPresent()).isEqualTo(false);
        assertThat(foundMember3.isPresent()).isEqualTo(false);
        assertThat(foundMember4.isPresent()).isEqualTo(false);
    }

    @Test
    void 회원_전체_조회() {
        // given
        Member member = Member.builder()
                .name("name1")
                .email("email@test.com")
                .password("test")
                .build();
        Member member2 = Member.builder()
                .name("name2")
                .email("email2@test.com")
                .password("test2")
                .build();
        this.memoryMemberRepository.save(member);
        this.memoryMemberRepository.save(member2);

        // when
        List<Member> members = this.memoryMemberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members.stream()
                .filter(_member -> _member.getId().equals(member.getId()))
                .findAny()
                .get()).isEqualTo(member);
        assertThat(members.stream()
                .filter(_member -> _member.getId().equals(member2.getId()))
                .findAny()
                .get()).isEqualTo(member2);
    }
}
