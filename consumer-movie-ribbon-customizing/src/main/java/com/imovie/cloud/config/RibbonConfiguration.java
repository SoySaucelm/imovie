package com.imovie.cloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该类为Ribbon的配置类
 * 注意: 该类不应该在主应用程序上下文的@componentScan中
 * 否则该类的配置信息就被所有的@RibbonClient共享
 * @author Created by liming on 2019/2/27.
 */
@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule() {
        //负载均衡规则 改为随机
        return new RandomRule();
    }

}
