package com.imovie.cloud.feign;

import com.imovie.cloud.entity.TUser;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liming on 2019/2/27.
 */
//@FeignClient(name = "imovie-user")
public interface UserFeignClient {

    @RequestMapping("user/{id}")
    TUser queryUserById(long id);
}
