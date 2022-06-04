package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.domain.model.Message

interface MessageService {
    suspend fun getAllMessages():List<Message>

    companion object {
        private const val BASE_URL ="http://172.26.192.1:8080"
        const val MESSAGES_URL = "$BASE_URL/messages"
    }
}