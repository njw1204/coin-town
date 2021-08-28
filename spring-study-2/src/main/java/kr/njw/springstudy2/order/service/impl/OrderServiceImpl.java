package kr.njw.springstudy2.order.service.impl;

import kr.njw.springstudy2.discount.service.DiscountPolicy;
import kr.njw.springstudy2.member.model.Member;
import kr.njw.springstudy2.member.repository.MemberRepository;
import kr.njw.springstudy2.order.model.Order;
import kr.njw.springstudy2.order.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = this.memberRepository.findById(memberId).orElseThrow();

        return Order.builder()
                .memberId(memberId)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .discountPrice(this.discountPolicy.discount(member,itemPrice))
                .build();
    }
}
