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
 * 3.JSR303
 *      1.给Bean添加校验注解 javax.validation.constraints 并定义自己的message提示
 *      2.开启校验功能 @Valid
 *          效果: 校验错误以后会有默认的响应
*       3. 给校验的bean后紧跟一个BindingResult 就可以获取到校验的结果
 *      4.分组校验
 *          1.@NotBlank(message="品牌名必须提交",groups={AddGroup.class,UpdateGroup.class})
 *          给校验注解标注什么情况需要进行校验
 *          2.@Validated({AddGroup.class})
 *          3.默认没有指定分组而定校验注解@NotBlank 在分组校验情况下@Validated({AddGroup.class})不生效.只会在@Validate情况下生效
 *      5.自定义校验
 *          1.编写一个自定义的校验注解 @ListValue
 *          2.编写一个自定义的校验器 ConstraintValidator
 *          3.关联自定义的校验器和自定义的校验注解
 *              @Documented
 *              @Constraint(validatedBy = { ListValueConstraintValidator.class <可以指定多个不同的校验器适配不同类型的校验器> })
 *              @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
 *              @Retention(RUNTIME)
 *              public @interface ListValue {
 *
 * 4.统一的异常处理
 * @ControllerAdvice
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
