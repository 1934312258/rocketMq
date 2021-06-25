package com.kevin.rocketMq.filter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/22 18:32
 */
public class ConsumerFiler {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("kevin_consumer");

        // Specify name server addresses.
        consumer.setNamesrvAddr("localhost:9876");

        // only subsribe messages have property a, also a >=0 and a <= 3
        // 过滤器只能使用一个，如果配置多个，则最后一个覆盖之前的
        // 过滤tags可以在sql中过滤
        // 字符变量时<>表示不等于
        // 连接符 and or not
//        consumer.subscribe("TopicTest", MessageSelector.bySql("a between 0 and 2 AND tags = zhao"));
//        consumer.subscribe("TopicTest", MessageSelector.bySql("a >= 90 AND b is not null"));
        consumer.subscribe("TopicTest", MessageSelector.bySql("a >= 90 and b in ('as','def','b')"));
//        consumer.subscribe("TopicTest", MessageSelector.bySql("a >= 90 AND b (IN bdf)"));
//        consumer.subscribe("TopicTest", "zhao || kevin || dfr");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(msgs.get(0).getTags());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
