package com.rabbitmq.chat.consumer.mapper;

import com.rabbitmq.chat.consumer.dto.MessageDto;
import com.rabbitmq.chat.model.domain.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MessageMapper {

    MessageDto convertToMessageDto(Message message);

    @Mapping(target = "id", ignore = true)
    Message convertToMessage(MessageDto messageDto);
}
