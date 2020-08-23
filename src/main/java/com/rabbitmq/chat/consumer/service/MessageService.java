package com.rabbitmq.chat.consumer.service;

import com.rabbitmq.chat.consumer.dao.MessageRepository;
import com.rabbitmq.chat.consumer.dto.MessageDto;
import com.rabbitmq.chat.consumer.mapper.MessageMapper;
import com.rabbitmq.chat.model.domain.Message;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public
class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    MessageService(MessageRepository messageRepository,
                   MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public Mono<Message> saveMessage(Message message) {
        log.info("Save message from queue: {}", message);
        return messageRepository.save(message);
    }

    Mono<List<MessageDto>> findAllMessagesBySender(String sender) {
        log.info("Finding all sender messages: {}", sender);
        return Try.of(() -> sender)
                .map(booleanResult -> messageRepository.findBySender(sender)
                        .map(messageMapper::convertToMessageDto)
                        .collectList())
                .getOrElse(() -> null);
    }

    Mono<List<MessageDto>> findAllMessagesByReceiver(String receiver) {
        log.info("Finding all receiver messages: {}", receiver);
        return Try.of(() -> receiver)
                .map(booleanResult -> messageRepository.findByReceiver(receiver)
                        .map(messageMapper::convertToMessageDto)
                        .collectList())
                .getOrElse(() -> null);
    }

}
