package com.wxw.mq.producer.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface RabbitMqSendService {

    /**
     * 发送消息
     * @param exchange   交换机
     * @param routingKey 路由key
     * @param message    消息
     */
    void sendMessage(String exchange, String routingKey, Object message);

    /**
     * 删除指定的交换机
     * @param exchangeName 交换机名
     */
    void deleteExchange(String exchangeName);

    /**
     * 消费消息：push方式
     * （1）具体实现是这么实现的，但是可以直接选择监听器来处理
     * （2）或者需要特殊处理消息的时候可以这么干
     * @param queueName 队列名称
     */
    void getPushMessage(String queueName);

    /**
     * 消费消息：pull方式
     * 一次消费一条
     * @param queueName 队列名称
     */
    void getPullMessage(String queueName);
}
