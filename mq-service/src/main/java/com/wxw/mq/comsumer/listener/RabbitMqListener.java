package com.wxw.mq.comsumer.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxw.mq.common.RabbitMqCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RabbitMqListener {

    /**
     * 监听队列 [test.queue] 消息
     * 相当于 PUSH 模式接收消息
     * @param message
     */
    @RabbitListener(queues = RabbitMqCommon.TEST_QUEUE_NAME)
    public void listenTestQueue(Message message) {
        String messageString = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("消费消息：queue=[{}], message.Body=[{}]", RabbitMqCommon.TEST_QUEUE_NAME, messageString);
    }


}
