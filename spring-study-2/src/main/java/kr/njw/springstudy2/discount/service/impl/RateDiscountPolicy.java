package kr.njw.springstudy2.discount.service.impl;

import kr.njw.springstudy2.discount.service.DiscountPolicy;
import kr.njw.springstudy2.member.constant.Grade;
import kr.njw.springstudy2.member.model.Member;

public class RateDiscountPolicy implements DiscountPolicy {
    @Override
    public int discount(Member member, int price) {
        final int DISCOUNT_PERCENT_FOR_VIP = 10;

        return (member.getGrade() == Grade.VIP) ? price * DISCOUNT_PERCENT_FOR_VIP / 100 : 0;
    }
}
