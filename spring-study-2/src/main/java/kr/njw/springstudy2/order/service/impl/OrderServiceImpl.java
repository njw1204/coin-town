package kr.njw.springstudy2.order.service.impl;

import kr.njw.springstudy2.discount.service.DiscountPolicy;
import kr.njw.springstudy2.discount.service.impl.RateDiscountPolicy;
import kr.njw.springstudy2.member.model.Member;
import kr.njw.springstudy2.member.repository.MemberRepository;
import kr.njw.springstudy2.member.repository.impl.MemoryMemberRepository;
import kr.njw.springstudy2.order.model.Order;
import kr.njw.springstudy2.order.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = this.memberRepository.findById(memberId).get();

        return Order.builder()
                .memberId(memberId)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .discountPrice(this.discountPolicy.discount(member,itemPrice))
                .build();
    }
}
