package kr.njw.springstudy2.config;

import kr.njw.springstudy2.discount.service.DiscountPolicy;
import kr.njw.springstudy2.discount.service.impl.RateDiscountPolicy;
import kr.njw.springstudy2.member.repository.MemberRepository;
import kr.njw.springstudy2.member.repository.impl.MemoryMemberRepository;
import kr.njw.springstudy2.member.service.MemberService;
import kr.njw.springstudy2.member.service.impl.MemberServiceImpl;
import kr.njw.springstudy2.order.service.OrderService;
import kr.njw.springstudy2.order.service.impl.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean("myDiscountPolicy")
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(this.memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(this.memberRepository(), this.discountPolicy());
    }
}
