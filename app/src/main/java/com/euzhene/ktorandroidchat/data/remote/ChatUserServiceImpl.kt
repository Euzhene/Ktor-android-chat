package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.USER_INFO_URL
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class ChatUserServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChatUserService {
    override suspend fun getUserInfo(userInfo: UserInfo): Resource<UserInfo?> {
        return try {
            val user = client.get<UserInfo>(
                USER_INFO_URL +
                        "?login=${userInfo.login}" +
                        "&password=${userInfo.password}" +
                        "&username=${userInfo.username}"
            )
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Unknown message")
        }
    }
}