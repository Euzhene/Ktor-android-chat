package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.MESSAGES_URL
import com.euzhene.ktorandroidchat.data.mapper.ChatMapper
import com.euzhene.ktorandroidchat.data.remote.dto.MessageDto
import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class MessageServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val mapper: ChatMapper
) : MessageService {
    override suspend fun getAllMessages(userInfo: UserInfo): Resource<List<Message>> {
        return try {
            val messageList = client.get<List<MessageDto>>(
                MESSAGES_URL +
                        "?login=${userInfo.login}" +
                        "&password=${userInfo.password}"
            ).map { mapper.mapDtoToEntity(it) }.asReversed()
            Resource.Success(messageList)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Unknown error")
        }
    }
}