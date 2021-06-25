package com.kevin;

import com.kevin.producer.ProducerCallBack;
import com.kevin.producer.SpringProducer;
import com.kevin.transaction.ProducerTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/6/24 15:16
 */
@RestController
public class controller {

    @Autowired
    SpringProducer producer;

    @Autowired
    ProducerCallBack callBack;

    @Autowired
    ProducerTransaction transaction;

    @RequestMapping("/test")
    public void test(){
        transaction.send();
    }
}
