package com.imovie.cloud.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author SoySauce
 * @date 2019/2/28
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    static class Food {
    }

    static class Fruit extends Food {
    }

    static class Apple extends Fruit {
    }

    static class RedApple extends Apple {
    }

    public static List<? extends Fruit> getObj(List<? super RedApple> list) {
        Apple apple = new Apple();
        ArrayList<Apple> objects = new ArrayList<>();
        objects.add(apple);
        return objects;
    }

    public static void main(String[] args) {
        int 大宝 = 9, 琦酱 = 3;
        Map<Object, Object> 琦酱he大宝 = new HashMap();
        琦酱he大宝.put(琦酱, "琦酱");
        琦酱he大宝.put(大宝, "大宝");
        System.out.println("大宝是9 琦酱是3 除了琦酱还是:"+琦酱he大宝.get(大宝 / 琦酱));


//        Apple a = new Apple();
//        List<Apple> objects = new ArrayList<>();
//        objects.add(a);
//        getObj(objects);
//
//        List<? extends Fruit> flist = new ArrayList<>();
//        flist.add(new Fruit());
    }

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有请求都需要经过 http basic 认证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        //明文编码器 不做任何操作的编码器
//        return NoOpPasswordEncoder.getInstance();// 过时
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Component
    class CustomUserDetailService implements UserDetailsService {
        /**
         * 模拟两个账户
         * 1:账户是user 密码 password1 角色user-role
         * 2:账户是admin 密码password2 角色admin-role
         *
         * @param username
         * @return
         * @throws UsernameNotFoundException
         */
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if ("user".equals(username)) {
                return new SecurityUser("user", "password1", "user-role");
            } else if ("admin".equals(username)) {
                return new SecurityUser("admin", "password2", "admin-role");
            }
            return null;
        }
    }

    @Data
    class SecurityUser implements UserDetails {
        private Long id;
        private String userName;
        private String passWord;
        private String role;

        public SecurityUser() {
        }

        public SecurityUser(String userName, String passWord, String role) {
            this.userName = userName;
            this.passWord = passWord;
            this.role = role;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.role);
            authorities.add(simpleGrantedAuthority);
            return authorities;
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


}
