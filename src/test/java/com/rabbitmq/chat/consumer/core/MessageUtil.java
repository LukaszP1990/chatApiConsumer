package com.rabbitmq.chat.consumer.core;

import com.rabbitmq.chat.consumer.dto.MessageDto;
import com.rabbitmq.chat.model.domain.Message;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MessageUtil {

    public static final String SENDER = "sender";
    public static final String RECEIVER = "receiver";
    public static final String MESSAGE = "test";

    public static List<Message> getMessages() {
        return IntStream.rangeClosed(1, 4)
                .mapToObj(value -> createMessage())
                .collect(Collectors.toList());
    }

    public static Message createMessage() {
        return Message.builder()
                .message(MESSAGE)
                .receiver(RECEIVER)
                .sender(SENDER)
                .createdDate(new Date())
                .build();
    }

    public static MessageDto createMessageDto() {
        return MessageDto.builder()
                .message(MESSAGE)
                .receiver(RECEIVER)
                .sender(SENDER)
                .createdDate(new Date())
                .build();
    }

    public static boolean isMessageDtoMatch(MessageDto messageDto) {
        return Objects.nonNull(messageDto) &&
                Objects.nonNull(messageDto.getCreatedDate()) &&
                MessageUtil.SENDER.equals(messageDto.getSender()) &&
                MessageUtil.RECEIVER.equals(messageDto.getReceiver()) &&
                MessageUtil.MESSAGE.equals(messageDto.getMessage());
    }

    public static boolean isMessageMatch(Message message) {
        return Objects.nonNull(message) &&
                Objects.nonNull(message.getCreatedDate()) &&
                MessageUtil.SENDER.equals(message.getSender()) &&
                MessageUtil.RECEIVER.equals(message.getReceiver()) &&
                MessageUtil.MESSAGE.equals(message.getMessage());
    }
}
