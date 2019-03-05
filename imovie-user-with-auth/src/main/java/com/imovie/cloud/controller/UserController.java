package com.imovie.cloud.controller;

import com.imovie.cloud.entity.TUser;
import com.imovie.cloud.mapper.TUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author soysauce
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private TUserMapper userMapper;

    @GetMapping("/{id}")
    public TUser queryUserById(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal:{}", principal);
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                log.info("当前用户是:{},角色是:{}", user.getUsername(), authority.getAuthority());
            }
        } else {
            log.info("纳尼... not instanceof UserDetail");
        }
        TUser tUser = userMapper.selectByPrimaryKey(id);
        return tUser;
    }
}
