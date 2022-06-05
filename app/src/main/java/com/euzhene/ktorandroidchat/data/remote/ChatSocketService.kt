package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession(userInfo: UserInfo): Resource<Unit>
    suspend fun sendMessage(message:String)
    fun observeMessages(): Flow<Message>
    suspend fun closeSession()

    companion object {
        private const val BASE_URL = "ws://192.168.31.152:8080"
        const val WEB_SOCKET_URL = "$BASE_URL/chat-socket"
    }
}