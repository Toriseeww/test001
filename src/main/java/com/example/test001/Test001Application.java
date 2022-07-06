package com.example.test001;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.test001.mapper")
public class Test001Application {
    public static void main(String[] args) {
        SpringApplication.run(Test001Application.class, args);
    }
}
