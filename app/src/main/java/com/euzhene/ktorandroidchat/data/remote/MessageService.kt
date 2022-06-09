package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource

interface MessageService {
    suspend fun getAllMessages(userInfo: UserInfo):Resource<List<Message>>
}