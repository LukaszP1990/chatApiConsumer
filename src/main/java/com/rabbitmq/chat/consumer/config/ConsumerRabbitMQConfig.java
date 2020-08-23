package com.rabbitmq.chat.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class ConsumerRabbitMQConfig {

    private static final String QUEUE_NAME = "message_queue";
    private static final String HOST_NAME = "localhost";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String TOPIC_EXCHANGE = "message_exchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(HOST_NAME);
        connectionFactory.setPort(5672);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory;
    }

    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue,
                    TopicExchange topicExchange){
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(QUEUE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter){
        log.info("host = " + connectionFactory.getHost());
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) throws MessageConversionException {
        return new MessageListenerAdapter(receiver, "receiveMsg");
    }
}
