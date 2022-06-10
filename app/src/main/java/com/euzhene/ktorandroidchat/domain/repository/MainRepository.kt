package com.euzhene.ktorandroidchat.domain.repository

import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun initSession(userInfo: UserInfo): Resource<Unit>
    suspend fun sendMessage(message:String): Resource<Unit>
    fun observeMessages(): Flow<Message>
    suspend fun closeSession()

    suspend fun getUserInfo(userInfo: UserInfo): Resource<UserInfo>
    suspend fun registerUser(userInfo: UserInfo):Resource<UserInfo>

    suspend fun getAllMessages(userInfo: UserInfo):Resource<List<Message>>
}