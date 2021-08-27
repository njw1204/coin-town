package kr.njw.springstudy2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SpringStudy2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringStudy2Application.class, args);
    }

    @Component
    public static class SpringStudy2CommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... args) {
            System.out.println("hello world");
        }
    }
}
