package com.wxw.springbootdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxw.cache.redis.service.RedisService;
import com.wxw.mq.common.RabbitMqCommon;
import com.wxw.mq.producer.service.RabbitMqSendService;
import com.wxw.springbootdemo.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GatewayServiceImpl implements GatewayService {

    @Resource
    private RabbitMqSendService rabbitMqSendService;
    @Resource
    private RedisService redisService;

    private static final String Peak_Clipping_Cache_Key = "Peak_Clipping_Key_";
    private static final String Cache_Key = "cacheKey";
    private static final String Message_Key = "message";

    @Override
    public String peakClipping(String param) {
        try {
            String cacheKey = Peak_Clipping_Cache_Key + UUID.randomUUID().toString();
            //TODO 校验是否达到流量限制（流量监控）
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Message_Key, param);
            jsonObject.put(Cache_Key, cacheKey);
            //放入MQ
            boolean sendSuccess = rabbitMqSendService.sendMessageToGatewayPeakClippingQueue(jsonObject.toJSONString());
            if (!sendSuccess) {
                log.info("消息发送MQ失败！");
                return "System Error!!!";
            }
            //从redis缓存中获取对应的消息
            while (true) {
                String result = (String) redisService.get(cacheKey);
                if (result == null) {
                    Thread.sleep(1000);
                } else {
                    return result;
                }
            }
        } catch (Exception e) {
            log.error("GatewayServiceImpl.peakClipping error", e);
            return "System Error!!!";
        }
    }

    /**
     * 消费网关数据
     */
    @RabbitListener(queues = RabbitMqCommon.QUEUE_GATEWAY_PEAK_CLIPPING)
    public void listenGatewayPeakClippingQueue(Message message) {
        log.info("QUEUE_GATEWAY_PEAK_CLIPPING 消息：");
        String messageString = new String(message.getBody(), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(messageString);
        String cacheKey = (String) jsonObject.get(Cache_Key);
        String requestMessage = (String) jsonObject.get(Message_Key);
        //处理数据
        String result = "请求数据：" + requestMessage + "。处理结果：成功！！！";
        redisService.set(cacheKey, result);
    }
}
