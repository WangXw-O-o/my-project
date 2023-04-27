package com.wxw.mq.producer.service.impl;

import com.rabbitmq.client.*;
import com.wxw.mq.common.RabbitMqCommon;
import com.wxw.mq.producer.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class RabbitMqServiceImpl implements RabbitMqService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    //设置10个线程来监听MQ
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    @Override
    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    @Override
    public void deleteExchange(String exchangeName) {
        try {
            ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false);
            channel.exchangeDelete(exchangeName);
        } catch (Exception e) {
            log.error("删除交换机{}异常", exchangeName, e);
        }
    }

    @Override
    public void getPushMessage(String queueName) {
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        try {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        String message = new String(body, StandardCharsets.UTF_8);
                        log.info("queue=[{}], getPushMessage=[{}]", queueName, message);
                        if (message.equals("exception")) {
                            throw new RuntimeException("exception");
                        }
                        //手动确认消息收到时应答成功。第二个参数等于true时，签收多条消息
                        channel.basicAck(envelope.getDeliveryTag(), true);
                    } catch (Exception e) {
                        log.error("接收消息异常！");
                        //第三个参数 requeue = true 时，消息重新回到队列，broker重新发送
                        channel.basicNack(envelope.getDeliveryTag(), true, true);
                    }
                }
            };
            //不能死循环一直接收消息，没收到消息时该方法不会阻塞，一直轮询Broker？
            while (true) {
//                log.info("wait...."); //会一直被打印
                channel.basicConsume(queueName, consumer);
            }
        } catch (Exception e) {
            log.error("消息消费异常", e);
        }
    }

    @Override
    public void getPullMessage(String queueName) {
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        try {
            int messageCount = basicGet(queueName, channel);
            while (messageCount > 0) {
                messageCount = basicGet(queueName, channel);
            }
        } catch (Exception e) {
            log.error("消息消费异常", e);
        }
    }

    private int basicGet(String queueName, Channel channel) throws IOException {
        //第二个参数，设置为false，队列中的消息不会被删除，但是能正常消费到全部的消息；
        //即messageCount计数会减少，但消息不会被删除
        GetResponse getResponse = channel.basicGet(queueName, true);
        String message = new String(getResponse.getBody(), StandardCharsets.UTF_8);
        log.info("queue=[{}], getPullMessage=[{}]", queueName, message);
        return getResponse.getMessageCount();
    }

    @Override
    public boolean sendMessageToGatewayPeakClippingQueue(String message) {
        try {
            sendMessage(RabbitMqCommon.EXCHANGE_GATEWAY_PEAK_CLIPPING,
                    RabbitMqCommon.ROUTING_KEY_GATEWAY_PEAK_CLIPPING,
                    message);
            return true;
        } catch (Exception e) {
            log.error("消息发送失败！", e);
            return false;
        }
    }

    @Override
    public void consumeWithFlowLimit(String queueName, int limit) {
        try {
            ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false);
            //给服务器设置prefetchCount：消费者一次最多能消费消息的数量
            channel.basicQos(0, limit, true);
            while (true) {
                pool.execute(()->{
                    //连接到队列
                    try {
                        channel.basicConsume(queueName, new DefaultConsumer(channel) {
                            @Override
                            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                                log.info("consumeWithFlowLimit consumer message={}", new String(body, StandardCharsets.UTF_8));
                                //手动确认消息
                                channel.basicAck(envelope.getDeliveryTag(), true);
                            }
                        });
                    } catch (IOException e) {
                        log.error("consumeWithFlowLimit Thread[{}] execute error:{}", Thread.currentThread().getName(), e);
                    }
                });
            }
        } catch (Exception e) {
            log.info("consumeWithFlowLimit error:", e);
        }
    }
}
