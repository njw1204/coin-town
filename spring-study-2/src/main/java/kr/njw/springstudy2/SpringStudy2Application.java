package kr.njw.springstudy2;

import kr.njw.springstudy2.config.AppConfig;
import kr.njw.springstudy2.member.constant.Grade;
import kr.njw.springstudy2.member.model.Member;
import kr.njw.springstudy2.member.service.MemberService;
import kr.njw.springstudy2.order.model.Order;
import kr.njw.springstudy2.order.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class SpringStudy2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringStudy2Application.class, args);
    }

    @Component
    @Profile("!test")
    public static class SpringStudy2CommandLineRunner implements CommandLineRunner {
        private final MemberService memberService;
        private final OrderService orderService;

        public SpringStudy2CommandLineRunner() {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            this.memberService = context.getBean("memberService", MemberService.class);
            this.orderService = context.getBean("orderService", OrderService.class);
        }

        @Override
        public void run(String... args) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("소비자가격: ");
            int itemPrice = Integer.parseInt(scanner.nextLine());
            Member member = new Member(ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE),
                    "테스트", Grade.VIP);

            this.memberService.join(member);
            Order order = this.orderService.createOrder(member.getId(), "사과", itemPrice);

            System.out.println(member);
            System.out.println(order);
            System.out.println("판매가격: " + order.calculatePrice());
        }
    }
}
