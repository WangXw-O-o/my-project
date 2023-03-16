package com.wxw.springbootdemo.mq;

import com.wxw.mq.common.RabbitMqCommon;
import com.wxw.mq.producer.service.RabbitMqSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMqTest {

    @Autowired
    private RabbitMqSendService rabbitMqSendService;

    @Test
    public void test() {
        rabbitMqSendService.sendMessage(RabbitMqCommon.TEST_EXCHANGE_NAME, "test.hello", "hello world");
    }

    @Test
    public void deleteExchange() throws IOException, TimeoutException {
        rabbitMqSendService.deleteExchange("boot_topic_exchange");
    }

    @Test
    public void testPushGet() {
        rabbitMqSendService.getPushMessage(RabbitMqCommon.TEST_PUSH_QUEUE_NAME);
    }

    @Test
    public void testPullGet() {
        rabbitMqSendService.getPullMessage(RabbitMqCommon.TEST_PULL_QUEUE_NAME);
    }

}
