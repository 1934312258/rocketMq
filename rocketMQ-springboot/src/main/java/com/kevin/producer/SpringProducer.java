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
 * @since 2021/6/24 15:06
 */
@Component
public class SpringProducer {

    @Autowired
    RocketMQTemplate template;

    String topic = "TestTopic";
    String hashKey = "kevin";
    public void send(){
        for(int i = 0; i< 1;i++){
            org.springframework.messaging.Message<String> message = MessageBuilder.withPayload("kevin"+i).build();
//            template.send(topic,message);
            // 此方法即可实现顺序消费，原理为使用hashkey与队列size取模，所有消息使用同一个队列
            // 同时消费端必须使用顺序消费模式ConsumeMode.CONCURRENTLY
//            template.sendOneWayOrderly(topic,message,hashKey);
//            template.syncSendOrderly(topic,message,hashKey);
            template.send(topic,message);
            template.sendOneWay(topic,message);
            String result = template.sendAndReceive(topic,message,String.class,hashKey);
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
            template.syncSendOrderly(topic,message,hashKey);
            template.asyncSendOrderly(topic,message,hashKey, new SendCallback(){
                @Override
                public void onSuccess(SendResult sendResult) {
                    if("SEND_OK".equals(sendResult.getSendStatus())){
                        System.out.println("success");
                    }
                }

                @Override
                public void onException(Throwable e) {

                }
            });
        }
    }

}
