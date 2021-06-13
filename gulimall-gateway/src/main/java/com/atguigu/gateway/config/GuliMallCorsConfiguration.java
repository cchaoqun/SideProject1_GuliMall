package com.atguigu.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author Chaoqun Cheng
 * @date 2021-06-2021/6/13-19:49
 */

//配置类
@Configuration
public class GuliMallCorsConfiguration {

    //注册到Spring中
    @Bean
    public CorsWebFilter corsWebFilter(){

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //1.配置跨域
        //允许所有的请求头类型跨域
        corsConfiguration.addAllowedHeader("*");
        //允许任意请求方式跨域
        corsConfiguration.addAllowedMethod("*");
        //允许任意请求来源跨域
        corsConfiguration.addAllowedOrigin("*");
        //允许携带cookies跨域
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}
