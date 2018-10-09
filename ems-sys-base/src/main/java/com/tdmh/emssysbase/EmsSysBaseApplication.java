package com.tdmh.emssysbase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tdmh.*")
@MapperScan(basePackages = "com.tdmh.entity.mapper")
public class EmsSysBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsSysBaseApplication.class, args);
    }
}
