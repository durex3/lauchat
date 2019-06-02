package com.durex.lauchat;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.durex", "org.n3r.idworker"})
@MapperScan(basePackages="com.durex.lauchat.mapper")
public class LauChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(LauChatApplication.class, args);
    }

}

