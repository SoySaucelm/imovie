package com.imovie.cloud.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liming on 2019/2/27.
 */
@Configuration
@RibbonClient(name = "imovie-user", configuration = RibbonConfiguration.class)
public class MyConfiguration {
}
