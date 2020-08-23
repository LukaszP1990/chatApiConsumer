package com.rabbitmq.chat.consumer;

import com.rabbitmq.chat.consumer.config.MessageReceiver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
class ChatApplicationConsumerTests {

	@Test
	void contextLoads() {
		MessageReceiver messageReceiver = mock(MessageReceiver.class);
	}

}
