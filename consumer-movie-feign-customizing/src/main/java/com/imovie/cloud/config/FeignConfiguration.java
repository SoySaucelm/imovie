package com.imovie.cloud.config;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该类为feign的配置类 不应该在主应用程序上下文的@componentScan中
 * 否则 该类的配置信息会被所以 feignClient 共享
 * @author SoySauce
 * @date 2019/2/28
 */
@Configuration
public class FeignConfiguration {
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

    /**
     * 为feign 添加拦截器 进行基于 HTTP basic 认证
     *
     * @return BasicAuthRequestInterceptor
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("root", "root");
    }
}
