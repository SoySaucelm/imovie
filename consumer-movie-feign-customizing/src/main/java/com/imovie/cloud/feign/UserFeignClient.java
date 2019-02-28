package com.imovie.cloud.feign;

import com.imovie.cloud.config.FeignConfiguration;
import com.imovie.cloud.entity.TUser;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * use @FeignClient configuration 指定 feign的配置类
 * @author SoySauce
 * @date 2019/2/28
 */
@FeignClient(name = "imovie-user",configuration = FeignConfiguration.class)
public interface UserFeignClient {

    /**
     * feign 自带注解@RequestLine
     * @param id
     * @return
     */
    @RequestLine("GET user/{id}")
    TUser queryUserById(@Param("id") long id);
}
