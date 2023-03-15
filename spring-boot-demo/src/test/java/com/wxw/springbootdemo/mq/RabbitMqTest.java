package com.wxw.springbootdemo.mq;

import com.wxw.mq.producer.config.RabbitMqConfig;
import com.wxw.mq.producer.service.RabbitMqSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMqTest {

    @Autowired
    private RabbitMqSendService rabbitMqSendService;

    @Test
    public void test() {
        rabbitMqSendService.sendMessage(RabbitMqConfig.EXCHANGE_NAME, "boot.hello", "hello world");
    }

}
