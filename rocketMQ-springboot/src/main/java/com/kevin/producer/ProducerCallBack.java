package com.kevin.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
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
 * @since 2021/6/24 16:59
 */
@Component
public class ProducerCallBack {
    @Autowired
    RocketMQTemplate template;

    String topic = "Topic";
    String hashKey = "kevin";
    public void send(){
        for(int i = 0; i< 1;i++){
            org.springframework.messaging.Message<String> message = MessageBuilder.withPayload("kevin"+i).build();
//            template.send(topic,message);
            // 此方法即可实现顺序消费，原理为使用hashkey与队列size取模，所有消息使用同一个队列
            // 同时消费端必须使用顺序消费模式ConsumeMode.CONCURRENTLY
//            template.sendOneWayOrderly(topic,message,hashKey);
//            template.syncSendOrderly(topic,message,hashKey);
//            String result = template.sendAndReceive(topic,message,String.class,hashKey);
            template.sendAndReceive(topic,message, new RocketMQLocalRequestCallback(){
                @Override
                public void onSuccess(Object message) {
                    System.out.println(message);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println(e);
                }
            });
        }
    }

}
