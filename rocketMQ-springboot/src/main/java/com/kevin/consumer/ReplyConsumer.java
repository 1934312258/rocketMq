package com.kevin.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/24 16:08
 * 类似于消息确认的功能，消费者消费后给生产者答复
 */
@Component
@RocketMQMessageListener(consumerGroup = "ConsumerGroup", topic = "Topic",consumeMode= ConsumeMode.CONCURRENTLY)
public class ReplyConsumer implements RocketMQReplyListener<String,String> {
    @Override
    public String onMessage(String message) {
        System.out.println(message);
//        throw new RuntimeException();
        return ConsumeConcurrentlyStatus.RECONSUME_LATER.toString();
    }
}
