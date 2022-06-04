package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession(): Result<Unit>
    suspend fun sendMessage()
    fun observeMessages(): Flow<Message>
    suspend fun closeSession()
}