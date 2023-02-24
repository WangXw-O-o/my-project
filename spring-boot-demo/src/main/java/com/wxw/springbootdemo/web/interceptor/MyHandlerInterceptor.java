package com.wxw.springbootdemo.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>拦截器</p>
 */
@Slf4j
@Configuration
public class MyHandlerInterceptor implements HandlerInterceptor {

    /**
     * 在控制器方法前执行
     * @param request
     * @param response
     * @param handler
     * @return
     *  true 继续执行
     *  false 中断后续的所有操作
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("拦截器 preHandle...");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 在控制器方法之后，解析试图前执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.debug("拦截器 postHandle...");
    }

    /**
     * 视图渲染结束后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        log.debug("拦截器 afterCompletion...");
    }
}
