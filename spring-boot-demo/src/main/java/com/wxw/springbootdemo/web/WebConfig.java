package com.wxw.springbootdemo.web;

import com.wxw.springbootdemo.web.interceptor.MyHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private MyHandlerInterceptor myHandlerInterceptor;

    /**
     * 增加拦截器到IOC容器中（项目启动注册）
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("拦截器 myHandlerInterceptor...");
        //不拦截的url
        final String[] commonExclude = {"/test/unInterceptor1","/test/unInterceptor2"};
        registry.addInterceptor(myHandlerInterceptor).excludePathPatterns(commonExclude);
    }
}
