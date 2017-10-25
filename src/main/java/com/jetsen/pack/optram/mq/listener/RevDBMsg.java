package com.jetsen.pack.optram.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * Created by lenovo on 2017/10/18.
 */
@Component
public class RevDBMsg {

    @RabbitListener(queues="change-db")
    public void processMessage(String content) {
        System.out.println("processing "+content);
    }

    @RabbitListener(queues="ok-change-db")
    public void processForDB(String content) {
        System.out.println("processing ok db "+content);
    }

}
