package com.wxw.mq.common;

public class RabbitMqCommon {

    public static final String TEST_EXCHANGE_NAME        = "test.topic.exchange";
    public static final String TEST_QUEUE_NAME           = "test.queue";
    public static final String TEST_PUSH_QUEUE_NAME      = "test.push.queue";
    public static final String TEST_PULL_QUEUE_NAME      = "test.pull.queue";
    public static final String TEST_ROUTING_KEY          = "test.#";

    //网关限流队列
    public static final String EXCHANGE_GATEWAY_PEAK_CLIPPING = "test.gateway.topic.exchange";
    public static final String QUEUE_GATEWAY_PEAK_CLIPPING = "test.gateway.peak.clipping";
    public static final String ROUTING_KEY_GATEWAY_PEAK_CLIPPING = "test.gateway.peak.clipping.#";


}
