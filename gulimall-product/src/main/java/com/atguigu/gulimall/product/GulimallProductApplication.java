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
 * 2. 逻辑删除
 *      https://mp.baomidou.com/guide/logic-delete.html#%E4%BD%BF%E7%94%A8%E6%96%B9%E6%B3%95
 *      1.配置全局的逻辑删除规则(省略)
 *      2.配置逻辑删除的组件Bean(省略)
 *      3.给Bean上加上逻辑删除注解 @TableLogic
 *
 *
 *
 */
@EnableDiscoveryClient //开启服务的注册与发现
@MapperScan("com.atguigu.gulimall.product.dao") //告诉mybatis-plus在这个路径找到我们的Mapper接口
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
