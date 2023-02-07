package com.wxw.springbootdemo.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * <p>过滤器——过滤所有请求</p>
 */
@Slf4j
// 注解方式注入到IOC容器中（需要启动类添加扫描器），也可以通过 FilterRegistrationBean 实例注入（可设置优先级等参数）
@WebFilter(filterName = "myFilter", urlPatterns = {"/*"})
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("过滤器 MyFilter...");
        //继续执行下一个过滤器
        filterChain.doFilter(servletRequest, servletResponse);
    }


}
