package com.wxw.mq.producer.service.impl;

import com.wxw.mq.producer.config.RabbitMqConfig;
import com.wxw.mq.producer.service.RabbitMqSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RabbitMqSendServiceImpl implements RabbitMqSendService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
