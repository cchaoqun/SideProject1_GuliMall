package com.atguigu.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 1. 远程调用别的服务
 *      引入open-feign
 *      编写接口, 告诉SpringCloud这个接口需要调用远程服务
 *      声明接口的每一个方法都是调用哪个远程服务的哪个请求
 */
@EnableFeignClients(basePackages = "com.atguigu.gulimall.member.feign")//所有远程调用的接口都在这个路径的包下
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
