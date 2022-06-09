package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource

interface ChatUserService {
    suspend fun getUserInfo(userInfo: UserInfo): Resource<UserInfo?>
}