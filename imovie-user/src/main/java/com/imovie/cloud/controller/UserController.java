package com.imovie.cloud.controller;

import com.imovie.cloud.entity.TUser;
import com.imovie.cloud.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author soysauce
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private TUserMapper userMapper;

    @GetMapping("/{id}")
    public TUser queryUserById(@PathVariable Long id) {
        TUser tUser = userMapper.selectByPrimaryKey(id);
        return tUser;
    }
}
