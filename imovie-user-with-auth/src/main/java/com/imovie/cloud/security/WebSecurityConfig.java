package com.imovie.cloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author SoySauce
 * @date 2019/2/28
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    static class Food{}
    static class Fruit extends Food{}
    static class Apple extends Fruit{}
    static class RedApple extends Apple{}

    public List<? super Fruit> getObj(){
        Apple apple = new Apple();
        ArrayList<Apple> objects = new ArrayList<>();
        objects.add(apple);
        return objects;
    }

    public static void main(String[] args){
        Fruit a = new RedApple();

        List<? extends Fruit> flist = new ArrayList<>();
//        flist.add(new Fruit());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有请求都需要经过 http basic 认证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //明文编码器 不做任何操作的编码器
//        return NoOpPasswordEncoder.getInstance();// 过时
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Component
    class CustomUserDetailService implements UserDetailsService{
        /**
         * 模拟两个账户
         * 1:账户是user 密码 password1 角色user-role
         * 2:账户是admin 密码password2 角色admin-role
         * @param username
         * @return
         * @throws UsernameNotFoundException
         */
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if ("user".equals(username)){
//                return new
            }
            return null;
        }
    }

    class SecurityUser implements UserDetails{
        private Long id;
        private String userName;
        private String passWord;
        private String role;

        public SecurityUser() {
        }

        public SecurityUser(String userName, String passWord,String role) {
            this.userName = userName;
            this.passWord = passWord;
            this.role = role;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
//            Collection
            return null;
        }

        @Override
        public String getPassword() {
            return passWord;
        }

        @Override
        public String getUsername() {
            return userName;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
    @Autowired
    private CustomUserDetailService customUserDetailService;
 }
