package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.data.remote.dto.MessageDto
import com.euzhene.ktorandroidchat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession():Result<Unit>
    suspend fun sendMessage()
    fun observeMessages(): Flow<Result<MessageDto>>
    suspend fun closeSession()
}