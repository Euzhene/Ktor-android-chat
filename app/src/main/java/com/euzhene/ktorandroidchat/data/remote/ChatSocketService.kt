package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession(userInfo: UserInfo): Resource<Unit>
    suspend fun sendMessage(message:String):Resource<Unit>
    fun observeMessages(): Flow<Message>
    suspend fun closeSession()
}