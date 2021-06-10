package com.atguigu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1. 引入Nacos Config starter 依赖
 * 2. 添加bootstrap.properties配置文件
 *    1. 配置当前服务的名称
 *    2. 配置nacos配置中心的地址
 * 3. 在配置中心添加一个数据集(Data Id) gulimall-coupon.properties
 *    1. 默认命名方式为 <应用名.properties>
 *    2. 在配置文件中可以随时修改信息发布
 * 4. 在需要获取到配置中心配置文件内容
 *      (@Value("{配置项的名}"))的类上
 *      添加 @RefreshScope注解,
 *      让该微服务可以动态的从配置中心配置文件获取内容
 * 5. 如果配置中心和当前应用的配置文件都配置了相同的项,优先使用配置中心的项
 */

//注册服务到注册中心
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
