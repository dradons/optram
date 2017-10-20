package com.jetsen.pack.optram.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lenovo on 2017/10/18.
 */
@Configuration
public class RabbitConfiguration {

    final static String queueName = "spring-boot";

    @Bean
    Queue queue(){
        return  new Queue(queueName,false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("queueName");//*.msg-inbox
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listenerAdapter);
        container.setQueueNames(queueName);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver){
        return  new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
