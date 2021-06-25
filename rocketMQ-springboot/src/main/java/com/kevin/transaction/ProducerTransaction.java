package com.kevin.transaction;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/25 11:32
 */
@Component
public class ProducerTransaction {

    @Autowired
    RocketMQTemplate template;

    String topic = "topic";

    public void send(){
        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload("kevin").build();
        TransactionSendResult result = template.sendMessageInTransaction(topic,message,topic);
        System.out.println(result);
    }
}
