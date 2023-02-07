package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j //日志注解
@RestController
public class HelloWorldController {

    @Resource
    private UserInfo userInfo;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "<html>" +
                "<head></head>" +
                "<body><font size='8' color='#6B238E'>Hello World</font></body>" +
                "</html>";
    }

    @RequestMapping("/test/userinfo")
    public String testUserInfo() {
        return userInfo.getName();
    }

    @RequestMapping("/test/log")
    public void testLog() {
        //包日志级别指定为debug，trace日志不输出
        log.trace("test......trace");
        log.debug("test......debug");
    }

    @RequestMapping("/test/unInterceptor1")
    public String testUnInterceptor1() {
        return "不执行拦截器的请求";
    }

    @RequestMapping("/test/exception")
    public void testException() {
//        throw new RepeatSubmitException("123456异常");
        throw new NullPointerException("123456异常");
    }

}
