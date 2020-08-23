package com.rabbitmq.chat.consumer.core;

import lombok.Getter;

@Getter
public enum MessageError {

    NO_MESSAGE_FROM_QUEUE("No message from queue"),
    NO_RECEIVER("No receiver to get messages"),
    NO_SENDER("No sender to get messages"),
    NO_SENDER_MSG("No sender message has been found in db"),
    NO_RECEIVER_MSG("No receiver message has been found in db");

    private final String text;

    MessageError(String text) {
        this.text = text;
    }
}
