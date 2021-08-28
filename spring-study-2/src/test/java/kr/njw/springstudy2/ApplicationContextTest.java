package kr.njw.springstudy2;

import kr.njw.springstudy2.config.AppConfig;
import kr.njw.springstudy2.discount.service.DiscountPolicy;
import kr.njw.springstudy2.discount.service.impl.RateDiscountPolicy;
import kr.njw.springstudy2.member.repository.MemberRepository;
import kr.njw.springstudy2.member.repository.impl.MemoryMemberRepository;
import kr.njw.springstudy2.member.service.MemberService;
import kr.njw.springstudy2.member.service.impl.MemberServiceImpl;
import kr.njw.springstudy2.order.service.OrderService;
import kr.njw.springstudy2.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ApplicationContextTest {
    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    static AnnotationConfigApplicationContext stubContext = new AnnotationConfigApplicationContext(
            ApplicationContextTest.StubConfiguration.class);

    @Test
    @DisplayName("스프링 빈 목록")
    void loadSpringApplicationBeans() {
        Arrays.stream(context.getBeanDefinitionNames())
                .filter(_name ->
                        context.getBeanDefinition(_name).getRole() == BeanDefinition.ROLE_APPLICATION)
                .forEach(_name -> {
                    System.out.println(_name + ": " + context.getBean(_name));
                });
    }

    @Test
    @DisplayName("xml 설정 테스트")
    void loadContextFromXml() {
        ApplicationContext xmlContext = new GenericXmlApplicationContext("app-context.xml");
        DiscountPolicy discountPolicy = xmlContext.getBean("myDiscountPolicy", DiscountPolicy.class);
        OrderService orderService = xmlContext.getBean(OrderService.class);
        MemberService memberService = xmlContext.getBean("memberService", MemberService.class);
        MemberRepository memberRepository = xmlContext.getBean("memberRepository", MemberRepository.class);

        assertThat(discountPolicy).isInstanceOf(RateDiscountPolicy.class);
        assertThat(orderService).isInstanceOf(OrderServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        assertThat(memberRepository).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    @DisplayName("스프링 빈 조회")
    void getBean() {
        DiscountPolicy discountPolicy = (DiscountPolicy)context.getBean("myDiscountPolicy"); // 이름
        OrderService orderService = context.getBean(OrderService.class); // 타입
        MemberService memberService = context.getBean("memberService", MemberService.class); // 이름 + 타입
        MemoryMemberRepository memberRepository = context.getBean(MemoryMemberRepository.class); // 구현체 타입
        NoSuchBeanDefinitionException exception = catchThrowableOfType(() ->
                context.getBean("asdf"), NoSuchBeanDefinitionException.class);

        assertThat(discountPolicy).isInstanceOf(RateDiscountPolicy.class);
        assertThat(orderService).isInstanceOf(OrderServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        assertThat(memberRepository).isInstanceOf(MemoryMemberRepository.class);
        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("중복 타입 빈이 등록되어 있을때 테스트")
    void getBeanOfDuplicatedType() {
        MemberRepository memberRepository1 = stubContext.getBean("memberRepository1", MemberRepository.class);
        MemberRepository memberRepository2 = stubContext.getBean("memberRepository2", MemberRepository.class);
        Map<String, MemberRepository> beansOfType = stubContext.getBeansOfType(MemberRepository.class);
        Map<String, Object> allBeans = stubContext.getBeansOfType(Object.class);

        beansOfType.forEach((_name, _memberRepository) ->
                System.out.println("name=" + _name + ", memberRepository=" + _memberRepository));

        allBeans.values().forEach(System.out::println);

        NoUniqueBeanDefinitionException exception = catchThrowableOfType(() ->
                stubContext.getBean(MemberRepository.class), NoUniqueBeanDefinitionException.class);

        assertThat(memberRepository1).isInstanceOf(MemoryMemberRepository.class);
        assertThat(memberRepository2).isInstanceOf(MemoryMemberRepository.class);
        assertThat(memberRepository1).isNotEqualTo(memberRepository2);

        assertThat(beansOfType.size()).isEqualTo(2);
        assertThat(beansOfType.get("memberRepository1")).isEqualTo(memberRepository1);
        assertThat(beansOfType.get("memberRepository2")).isEqualTo(memberRepository2);

        assertThat(allBeans.values().stream().filter(_bean -> _bean instanceof MemoryMemberRepository).count())
                .isEqualTo(2);
        assertThat(allBeans.values().stream().filter(_bean -> _bean instanceof String).count())
                .isEqualTo(2);

        assertThat(exception).isNotNull();
    }

    @Configuration
    static class StubConfiguration {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }

        @Bean
        public String hello() {
            return "Hello";
        }

        @Bean
        public String world() {
            return "world";
        }
    }
}
