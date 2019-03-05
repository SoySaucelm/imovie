package com.imovie.cloud.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SoySauce
 * @date 2019/3/5
 */
@Configuration
public class FeignLogConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {

        return Logger.Level.FULL;
    }
}
