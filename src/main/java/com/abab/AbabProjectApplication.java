package com.abab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.abab.mapper")
public class AbabProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbabProjectApplication.class, args);
    }

}
