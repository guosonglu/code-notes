package com.luguosong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.luguosong.mapper")
@SpringBootApplication
public class NacosClientUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosClientUserApplication.class, args);
    }
}
