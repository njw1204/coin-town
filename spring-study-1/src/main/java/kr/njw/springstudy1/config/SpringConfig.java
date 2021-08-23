package kr.njw.springstudy1.config;

import kr.njw.springstudy1.repository.MemberRepository;
import kr.njw.springstudy1.repository.impl.JdbcMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager entityManager;

    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager entityManager) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(this.dataSource);
    }
}
