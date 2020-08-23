package com.rabbitmq.chat.consumer;

import com.rabbitmq.chat.consumer.config.MessageReceiver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ChatApplicationConsumer implements CommandLineRunner {

	public static void main(String[] args) {
        SpringApplication.run(ChatApplicationConsumer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var receiver = new MessageReceiver();
        receiver.getCountDownLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
