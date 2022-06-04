package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.data.mapper.ChatMapper
import com.euzhene.ktorandroidchat.data.remote.dto.MessageDto
import com.euzhene.ktorandroidchat.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class MessageServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val mapper: ChatMapper
) : MessageService {
    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDto>>(MessageService.MESSAGES_URL)
                .map { mapper.mapDtoToEntity(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

}