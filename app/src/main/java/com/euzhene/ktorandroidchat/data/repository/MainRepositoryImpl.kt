package com.euzhene.ktorandroidchat.data.repository

import com.euzhene.ktorandroidchat.data.remote.ChatSocketService
import com.euzhene.ktorandroidchat.data.remote.ChatUserService
import com.euzhene.ktorandroidchat.data.remote.MessageService
import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.domain.repository.MainRepository
import com.euzhene.ktorandroidchat.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val chatSocketService: ChatSocketService,
    private val chatUserService: ChatUserService,
    private val messageService: MessageService,
) : MainRepository {
    override suspend fun initSession(userInfo: UserInfo): Resource<Unit> {
        return chatSocketService.initSession(userInfo)
    }

    override suspend fun sendMessage(message: String): Resource<Unit> {
        return chatSocketService.sendMessage(message)
    }

    override fun observeMessages(): Flow<Message> {
        return chatSocketService.observeMessages()
    }

    override suspend fun closeSession() {
        return chatSocketService.closeSession()
    }

    override suspend fun getUserInfo(userInfo: UserInfo): Resource<UserInfo> {
        return chatUserService.getUserInfo(userInfo)
    }

    override suspend fun registerUser(userInfo: UserInfo): Resource<UserInfo> {
        return chatUserService.registerUser(userInfo)
    }

    override suspend fun getAllMessages(userInfo: UserInfo): Resource<List<Message>> {
        return messageService.getAllMessages(userInfo)
    }
}