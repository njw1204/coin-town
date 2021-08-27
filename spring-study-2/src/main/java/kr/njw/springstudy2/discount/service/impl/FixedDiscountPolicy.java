package kr.njw.springstudy2.discount.service.impl;

import kr.njw.springstudy2.discount.service.DiscountPolicy;
import kr.njw.springstudy2.member.constant.Grade;
import kr.njw.springstudy2.member.model.Member;

public class FixedDiscountPolicy implements DiscountPolicy {
    @Override
    public int discount(Member member, int price) {
        final int DISCOUNT_PRICE_FOR_VIP = 1000;

        int shouldDiscount = (member.getGrade() == Grade.VIP) ? DISCOUNT_PRICE_FOR_VIP : 0;
        return Math.min(shouldDiscount, price);
    }
}
