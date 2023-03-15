package com.wxw.mq.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_queue";
    public static final String ROUTING_KEY = "boot.#";

    //交换机
    @Bean("bootExchange")
    public Exchange bootExchange() {
        //durable:是否持久化
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //队列
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 绑定队列和交换机
     * 1、
     */
    @Bean
    public Binding bindQueueAndExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }

}
