package com.jetsen.pack.optram.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lenovo on 2017/10/18.
 */
//@Configuration
public class CustomerMQCfg {
    final static String customer_queueName = "customer";

    @Bean
    Queue cusQueue(){
        return new Queue(customer_queueName,false);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("customer-exchange");
    }

}
