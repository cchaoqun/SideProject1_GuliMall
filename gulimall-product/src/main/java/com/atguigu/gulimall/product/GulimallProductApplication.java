package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1. 整合Mybatis-Plus
 *      导入依赖
 *      <dependency>
 *          <groupId>com.baomidou</groupId>
 *          <artifactId>mybatis-plus-boot-starter</artifactId>
 *          <version>3.2.0</version>
 *      </dependency>
 *      配置
 *          配置数据源
 *              导入数据库驱动
 *              gulimall-product模块下创建application.yml配置数据源
 *          配置mybatis-plus
 *              使用@MapperScan告诉Mybatis-plus Mapper接口的位置
 *              告诉Mybatis-plus映射文件位置
 *              配置主键自增
 *
 *
 *
 *
 *
 */
@EnableDiscoveryClient
@MapperScan("com.atguigu.gulimall.product.dao") //告诉mybatis-plus在这个路径找到我们的Mapper接口
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
