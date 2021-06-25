package com.kevin.rocketMq.filter;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;


/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/22 18:35
 */
public class ProducerFilter {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        String tag = "zhao";
        for(int i = 0; i < 100; i++) {
            Message msg = new Message("TopicTest",
                    tag,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // Set some properties.
            msg.putUserProperty("a", String.valueOf(i));
            msg.putUserProperty("b", "b");
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult.getSendStatus());
        }
        producer.shutdown();
    }
}
