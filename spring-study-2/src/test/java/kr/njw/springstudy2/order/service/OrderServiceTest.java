package kr.njw.springstudy2.order.service;

import kr.njw.springstudy2.config.AppConfig;
import kr.njw.springstudy2.member.constant.Grade;
import kr.njw.springstudy2.member.model.Member;
import kr.njw.springstudy2.member.service.MemberService;
import kr.njw.springstudy2.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        this.memberService = appConfig.memberService();
        this.orderService = appConfig.orderService();
    }

    @Test
    void 주문생성() {
        // given
        Long memberId = 81L;
        Long memberBasicId = 3L;
        Member member = new Member(memberId, "name_1", Grade.VIP);
        Member memberBasic = new Member(memberBasicId, "이름", Grade.BASIC);
        this.memberService.join(member);
        this.memberService.join(memberBasic);

        // when
        Order order = this.orderService.createOrder(memberId, "오렌지", 5000);
        Order lowPriceOrder = this.orderService.createOrder(memberId, "껌", 10);
        Order orderForBasic = this.orderService.createOrder(memberBasicId, "사과", 10000);

        // then
        assertThat(order.getMemberId()).isEqualTo(member.getId());
        assertThat(order.getItemName()).isEqualTo("오렌지");
        assertThat(order.getItemPrice()).isEqualTo(5000);
        assertThat(order.getDiscountPrice()).isEqualTo(500);
        assertThat(order.calculatePrice()).isEqualTo(order.getItemPrice() - order.getDiscountPrice());

        assertThat(lowPriceOrder.getItemPrice()).isEqualTo(10);
        assertThat(lowPriceOrder.getDiscountPrice()).isEqualTo(1);
        assertThat(lowPriceOrder.calculatePrice()).isEqualTo(lowPriceOrder.getItemPrice() - lowPriceOrder.getDiscountPrice());

        assertThat(orderForBasic.getDiscountPrice()).isEqualTo(0);
        assertThat(orderForBasic.calculatePrice()).isEqualTo(10000);
    }
}
