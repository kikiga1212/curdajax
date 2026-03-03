package com.example.curdajax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//TODO: JPA 감사 활성화(날짜, 시간 자동생성)
@EnableJpaAuditing
public class CurdajaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurdajaxApplication.class, args);
    }

}
