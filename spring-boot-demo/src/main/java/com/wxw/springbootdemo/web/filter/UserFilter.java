package com.wxw.springbootdemo.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * <p>过滤器——过滤 /user/* 请求</p>
 */
@Slf4j
@WebFilter(filterName = "secondFilter", urlPatterns = "/user/*")
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("过滤器 UserFilter...");
        //继续执行下一个过滤器
        filterChain.doFilter(servletRequest, servletResponse);
    }


}
