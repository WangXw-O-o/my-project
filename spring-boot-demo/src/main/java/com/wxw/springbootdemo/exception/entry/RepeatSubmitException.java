package com.wxw.springbootdemo.exception.entry;

/**
 * <p>自定义的重复请求异常类</p>
 */
public class RepeatSubmitException extends RuntimeException {

    public RepeatSubmitException(String message) {
        super(message);
    }
}
