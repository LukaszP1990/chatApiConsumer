package com.rabbitmq.chat.consumer.resource;

import com.rabbitmq.chat.consumer.service.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class MessageRouting {

    private static final String PATH = "/api/message-consumer";
    private static final String SENDER = "/sender/messages";
    private static final String RECEIVER = "/receiver/messages";

    @Bean
    public RouterFunction<ServerResponse> routes(MessageHandler handler) {
        return RouterFunctions
                .route(GET(PATH.concat(SENDER)), handler::findAllMessagesBySender)
                .andRoute(GET(PATH.concat(RECEIVER)), handler::findAllMessagesByReceiver);
//                .andRoute(GET(PATH), serverRequest -> handler.getMessage());
    }
}
