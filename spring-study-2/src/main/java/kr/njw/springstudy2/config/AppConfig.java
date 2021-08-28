package kr.njw.springstudy2.config;

import kr.njw.springstudy2.discount.service.DiscountPolicy;
import kr.njw.springstudy2.discount.service.impl.RateDiscountPolicy;
import kr.njw.springstudy2.member.repository.MemberRepository;
import kr.njw.springstudy2.member.repository.impl.MemoryMemberRepository;
import kr.njw.springstudy2.member.service.MemberService;
import kr.njw.springstudy2.member.service.impl.MemberServiceImpl;
import kr.njw.springstudy2.order.service.OrderService;
import kr.njw.springstudy2.order.service.impl.OrderServiceImpl;

public class AppConfig {
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    public MemberService memberService() {
        return new MemberServiceImpl(this.memberRepository());
    }

    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(this.memberRepository(), this.discountPolicy());
    }
}
