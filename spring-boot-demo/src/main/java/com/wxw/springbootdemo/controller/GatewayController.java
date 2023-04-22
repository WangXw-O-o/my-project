package com.wxw.springbootdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxw.cache.redis.service.RedisService;
import com.wxw.mq.producer.service.RabbitMqSendService;
import com.wxw.springbootdemo.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@Slf4j
public class GatewayController {

    @Resource
    private GatewayService gatewayService;

    /**
     * 削峰接口
     */
    @GetMapping("/gateway/peak/clipping/{param}")
    @ResponseBody
    public String peakClipping(@PathVariable("param") String param) {
        try {
            if (param == null || "".equals(param)) {
                return "param can't be null!!!";
            }
            return gatewayService.peakClipping(param);
        } catch (Exception e) {
            log.error("GatewayController.peakClipping error", e);
        }
        return "System Error!!!";
    }
}
