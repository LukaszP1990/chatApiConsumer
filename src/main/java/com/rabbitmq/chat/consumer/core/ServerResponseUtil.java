package com.rabbitmq.chat.consumer.core;

import com.rabbitmq.chat.consumer.dto.MessageDto;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ServerResponseUtil {

    public static <T> Mono<ServerResponse> ok(T messages) {
        return ServerResponse
                .ok()
                .body(BodyInserters.fromObject(messages));
    }

    public static Mono<ServerResponse> badRequest(MessageError messageError) {
        return ServerResponse
                .badRequest()
                .body(BodyInserters.fromObject(getMessageDto(messageError)));
    }

    private static MessageDto getMessageDto(MessageError messageError) {
        return MessageDto.builder()
                .message(messageError.getText())
                .build();
    }
}
