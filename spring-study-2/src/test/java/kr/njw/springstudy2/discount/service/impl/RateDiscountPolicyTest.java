package kr.njw.springstudy2.discount.service.impl;

import kr.njw.springstudy2.member.constant.Grade;
import kr.njw.springstudy2.member.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RateDiscountPolicyTest {
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인해줘야 한다")
    void VIP_할인적용() {
        // given
        Member member = new Member(1L, "나는 VIP", Grade.VIP);

        // when
        int discount = this.discountPolicy.discount(member, 1000);
        int discount2 = this.discountPolicy.discount(member, 123456);
        int discount3 = this.discountPolicy.discount(member, 12345);
        int discount4 = this.discountPolicy.discount(member, 1234);
        int discount5 = this.discountPolicy.discount(member, 10);
        int discount6 = this.discountPolicy.discount(member, 2);
        int discount7 = this.discountPolicy.discount(member, 1);

        // then
        assertThat(discount).isEqualTo(100);
        assertThat(discount2).isEqualTo(12345);
        assertThat(discount3).isEqualTo(1234);
        assertThat(discount4).isEqualTo(123);
        assertThat(discount5).isEqualTo(1);
        assertThat(discount6).isEqualTo(0);
        assertThat(discount7).isEqualTo(0);
    }

    @Test
    @DisplayName("VIP가 아니면 할인해주면 안된다")
    void 일반회원_할인미적용() {
        // given
        Member member = new Member(1L, "테스트", Grade.BASIC);

        // when
        int discount = this.discountPolicy.discount(member, 1000);
        int discount2 = this.discountPolicy.discount(member, 12345);
        int discount3 = this.discountPolicy.discount(member, 10);
        int discount4 = this.discountPolicy.discount(member, 2);
        int discount5 = this.discountPolicy.discount(member, 1);

        // then
        assertThat(discount).isEqualTo(0);
        assertThat(discount2).isEqualTo(0);
        assertThat(discount3).isEqualTo(0);
        assertThat(discount4).isEqualTo(0);
        assertThat(discount5).isEqualTo(0);
    }
}
