package com.euzhene.ktorandroidchat.data.mapper

import com.euzhene.ktorandroidchat.data.remote.dto.MessageDto
import com.euzhene.ktorandroidchat.domain.model.Message

class ChatMapper() {
    fun mapDtoToEntity(messageDto: MessageDto) = Message(
        text = messageDto.text,
        username = messageDto.text,
        timestamp = messageDto.timestamp
    )
}