package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.USER_INFO_URL
import com.euzhene.ktorandroidchat.USER_REGISTRATION_URL
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class ChatUserServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChatUserService {
    override suspend fun getUserInfo(userInfo: UserInfo): Resource<UserInfo> {
        return try {
            val userResponse = client.get<UserInfo>(
                USER_INFO_URL +
                        "?login=${userInfo.login}" +
                        "&password=${userInfo.password}" +
                        "&username=${userInfo.username}"
            )
            Resource.Success(userResponse)
        } catch (e: ClientRequestException) {
            Resource.Error(message = e.response.readText())
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Unknown message")
        }
    }

    override suspend fun registerUser(userInfo: UserInfo): Resource<UserInfo> {
        return try {
            val result = client.get<UserInfo>(
                USER_REGISTRATION_URL +
                        "?login=${userInfo.login}" +
                        "&password=${userInfo.password}" +
                        "&username=${userInfo.username}"
            )
            Resource.Success(result)
        } catch (e: ClientRequestException) {
            Resource.Error(message = e.response.readText())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}