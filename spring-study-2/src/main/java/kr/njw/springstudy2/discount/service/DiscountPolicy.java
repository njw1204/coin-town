package kr.njw.springstudy2.discount.service;

import kr.njw.springstudy2.member.model.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
