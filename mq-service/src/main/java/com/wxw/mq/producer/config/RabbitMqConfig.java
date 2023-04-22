package com.wxw.mq.producer.config;

import com.wxw.mq.common.RabbitMqCommon;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean("testExchange")
    public Exchange testExchange() {
        //durable:是否持久化
        return ExchangeBuilder
                .topicExchange(RabbitMqCommon.TEST_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    //队列
    @Bean("testQueue")
    public Queue testQueue() {
        return QueueBuilder
                .durable(RabbitMqCommon.TEST_QUEUE_NAME)
                .build();
    }

    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding bindQueueAndExchange(@Qualifier("testQueue") Queue queue, @Qualifier("testExchange") Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(RabbitMqCommon.TEST_ROUTING_KEY)
                .noargs();
    }


    /**
     * 网关交换机
     */
    @Bean("gatewayExchange")
    public Exchange gatewayExchange() {
        return ExchangeBuilder
                .topicExchange(RabbitMqCommon.EXCHANGE_GATEWAY_PEAK_CLIPPING)
                .durable(true)
                .build();
    }

    /**
     * 网关削峰队列
     */
    @Bean("gatewayPeakClippingQueue")
    public Queue gatewayPeakClippingQueue() {
        return QueueBuilder
                .durable(RabbitMqCommon.QUEUE_GATEWAY_PEAK_CLIPPING)
                .build();
    }

    /**
     * 绑定网关交换机和网关削峰队列
     */
    @Bean
    public Binding bindGatewayExchangeAndPeakClippingQueue(@Qualifier("gatewayExchange") Exchange exchange, @Qualifier("gatewayPeakClippingQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(RabbitMqCommon.ROUTING_KEY_GATEWAY_PEAK_CLIPPING)
                .noargs();
    }
}
