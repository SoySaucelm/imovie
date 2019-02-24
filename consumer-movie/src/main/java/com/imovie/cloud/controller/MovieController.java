package com.imovie.cloud.controller;

import com.imovie.cloud.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author soysauce
 */
@RestController
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("user/{id}")
    public TUser queryUserById(@PathVariable long id){
        return restTemplate.getForObject("http://localhost:8080/user/" + id, TUser.class);
    }
}
