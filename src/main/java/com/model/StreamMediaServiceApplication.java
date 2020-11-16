package com.model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.model.mapper")
@SpringBootApplication
public class StreamMediaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamMediaServiceApplication.class, args);
        System.out.println("-----------------------------------------------------");
        System.out.println("启动成功。。。。");
        System.out.println("-----------------------------------------------------");
    }

}
