package com.imovie.cloud.feign;

import com.imovie.cloud.entity.TUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liming on 2019/2/27.
 */
@FeignClient(name = "imovie-user",configuration = FeignLogConfiguration.class)
public interface UserFeignClient {

    @RequestMapping("user/{id}")
    TUser queryUserById(long id);
}
