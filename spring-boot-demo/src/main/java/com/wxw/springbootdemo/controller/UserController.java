package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.model.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserInfo userInfo;

    @GetMapping("/{id}")
    public Object getById(@PathVariable("id") String id) {
        userInfo.setId(id);
        return userInfo;
    }

}
