package com.wxw.springbootdemo.exception;

import lombok.Data;

@Data
public class ResultResponse {

    private String msg;

    public ResultResponse(String msg) {
        this.msg = msg;
    }
}
