package com.zbf.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: zhuyiwen
 * 作者: zhuyiwen
 * 日期: 2020/9/8  23:46
 * 描述: 用户服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zbf"})
@EnableFeignClients
public class UserServerApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServerApp.class);
    }
}
