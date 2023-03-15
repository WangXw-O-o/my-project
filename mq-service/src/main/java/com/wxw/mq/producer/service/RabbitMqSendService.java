package com.wxw.mq.producer.service;

public interface RabbitMqSendService {

    void sendMessage(String exchange, String routingKey, Object message);

}
