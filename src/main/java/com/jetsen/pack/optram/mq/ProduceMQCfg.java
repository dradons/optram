package com.jetsen.pack.optram.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lenovo on 2017/10/18.
 */
@Configuration
public class ProduceMQCfg {

    final static String produce_queueName = "change-db";
    final static String ok_queueName = "ok-change-db";

    @Bean
    Queue cusQueue(){
        return new Queue(produce_queueName,false);
    }

    @Bean
    Queue okQueue(){
        return new Queue(ok_queueName,false);
    }

    @Bean
    TopicExchange changeDBExchange(){
        return new TopicExchange("change-db-exchange");
    }

    @Bean
    Binding changeDBBinding(Queue cusQueue, TopicExchange changeDBExchange){
        return BindingBuilder.bind(cusQueue).to(changeDBExchange).with("*.change-db");
    }

    @Bean
    Binding okChangeDBBinding(Queue okQueue, TopicExchange changeDBExchange){
        return BindingBuilder.bind(okQueue).to(changeDBExchange).with("ok.change-db");
    }
}
