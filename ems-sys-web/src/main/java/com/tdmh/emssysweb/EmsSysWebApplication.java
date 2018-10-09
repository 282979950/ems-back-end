package com.tdmh.emssysweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tdmh.*"})
@MapperScan(basePackages = "com.tdmh.entity.mapper")
public class EmsSysWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsSysWebApplication.class, args);
    }
}
