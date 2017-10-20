package com.jetsen.pack.optram.mq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lenovo on 2017/10/18.
 */
@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message){
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch(){
        return latch;
    }
}
