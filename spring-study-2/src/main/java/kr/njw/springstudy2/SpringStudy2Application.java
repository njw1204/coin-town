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
import org.springframework.stereotype.Component;

import java.util.Scanner;

@SpringBootApplication
public class SpringStudy2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringStudy2Application.class, args);
    }

    @Component
    public static class SpringStudy2CommandLineRunner implements CommandLineRunner {
        private final AppConfig appConfig = new AppConfig();
        private final MemberService memberService = appConfig.memberService();
        private final OrderService orderService = appConfig.orderService();

        @Override
        public void run(String... args) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("소비자가격: ");
            int itemPrice = Integer.parseInt(scanner.nextLine());
            Member member = new Member(1L, "테스트", Grade.VIP);

            this.memberService.join(member);
            Order order = this.orderService.createOrder(member.getId(), "사과", itemPrice);

            System.out.println(member);
            System.out.println(order);
            System.out.println("판매가격: " + order.calculatePrice());
        }
    }
}
