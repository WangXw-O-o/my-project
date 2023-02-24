package com.wxw.springbootdemo.exception;

import com.wxw.springbootdemo.exception.entry.RepeatSubmitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局统一的异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(NullPointerException.class)
    public String onException(NullPointerException exception) {
        log.error("空指针异常！" + exception.getMessage());
        return "空指针异常";
    }

    /**
     * 自定义重复提交请求的异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(RepeatSubmitException.class)
    public ResultResponse onException(RepeatSubmitException exception) {
        log.error("重复提交请求异常！" + exception.getMessage());
        return new ResultResponse(exception.getMessage());
    }

}
