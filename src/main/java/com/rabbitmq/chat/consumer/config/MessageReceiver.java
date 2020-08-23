package com.rabbitmq.chat.consumer.config;

import com.rabbitmq.chat.consumer.service.MessageService;
import com.rabbitmq.chat.model.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class MessageReceiver {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private MessageService messageService;

    public void receiveMsg(Message message) {
        log.info("message object is received : {}", message);
        messageService.saveMessage(message)
                .subscribe(msg->
                                log.info("message saved in consumer db {}", msg),
                        thr -> log.error("message has not been saved in consumer db "));
        countDownLatch.countDown();
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
