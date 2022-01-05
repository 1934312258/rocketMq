package com.kevin.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/25 11:42
 */
@RocketMQTransactionListener
public class TransactionImpl implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println(msg);
        System.out.println(arg);
        // return null 时也会重试
        // 事务消息的使用在执行本地事务时执行本地数据库事务加远程调用
        // 如果远程调用失败则回滚本地事务，然后使用检查本地事务的方式进行重试
        return null;
//        return RocketMQLocalTransactionState.UNKNOWN;
    }


    /**
     *
     * 消息回查，只有在本地事务执行后服务端收不到回复才会触发
     * */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        System.out.println(msg);
        return null;
    }
}
