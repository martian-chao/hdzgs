package com.cars.test;

import com.cars.util.queue.QueueName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 测试
 * Created by liuyanchao
 * on 2018/8/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSend {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 货调汇总数据
     */
    @Test
    public void testSendHdhz() {
            String json = "[{\"zcrq\":\"20180818\",\"lwdw\":\"KFM00\",\"seqnum\":\"930628\",\"dfjhz\":\"汉字\"}]";
//            rabbitTemplate.convertAndSend(QueueName.HD_HDHZ_QUEUE, json.getBytes());
            rabbitTemplate.convertAndSend(QueueName.HD_HDHZ_QUEUE, json);
    }

    /**
     * 货调落成日期
     */
    @Test
    public void testSendHdLcrq() {
            String json = "{\"ljdm\":\"B\",\"lcrq\":\"20180815\"}";
            rabbitTemplate.convertAndSend(QueueName.HD_LCRQHZ_QUEUE, json.getBytes());
//        }
    }


}
