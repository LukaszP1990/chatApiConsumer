package com.rabbitmq.chat.consumer.service;

import com.rabbitmq.chat.consumer.core.MessageError;
import com.rabbitmq.chat.consumer.core.ServerResponseUtil;
import com.rabbitmq.chat.consumer.dto.MessageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public
class MessageHandler {

    private static final String SENDER = "sender";
    private static final String RECEIVER = "receiver";
    private final MessageService messageService;

    MessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    public Mono<ServerResponse> findAllMessagesBySender(ServerRequest serverRequest) {
        return Mono.just(serverRequest.queryParam(SENDER))
                .flatMap(Mono::justOrEmpty)
                .map(messageService::findAllMessagesBySender)
                .flatMap(senderMessages -> getMessages(senderMessages, MessageError.NO_SENDER_MSG))
                .switchIfEmpty(ServerResponseUtil.badRequest(MessageError.NO_SENDER));
    }

    public Mono<ServerResponse> findAllMessagesByReceiver(ServerRequest serverRequest) {
        return Mono.just(serverRequest.queryParam(RECEIVER))
                .flatMap(Mono::justOrEmpty)
                .map(messageService::findAllMessagesByReceiver)
                .flatMap(senderMessages -> getMessages(senderMessages, MessageError.NO_RECEIVER_MSG))
                .switchIfEmpty(ServerResponseUtil.badRequest(MessageError.NO_RECEIVER));
    }

    private Mono<ServerResponse> getMessages(Mono<List<MessageDto>> senderMessages,
                                             MessageError messageError) {
        return senderMessages
                .filter(messageDtoList -> !messageDtoList.isEmpty())
                .flatMap(ServerResponseUtil::ok)
                .switchIfEmpty(ServerResponseUtil.badRequest(messageError));
    }
}
