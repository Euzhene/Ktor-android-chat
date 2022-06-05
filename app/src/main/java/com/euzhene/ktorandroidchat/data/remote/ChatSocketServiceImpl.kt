package com.euzhene.ktorandroidchat.data.remote

import com.euzhene.ktorandroidchat.data.mapper.ChatMapper
import com.euzhene.ktorandroidchat.data.remote.ChatSocketService.Companion.WEB_SOCKET_URL
import com.euzhene.ktorandroidchat.data.remote.dto.MessageDto
import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.utils.Resource
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChatSocketServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val mapper: ChatMapper
) : ChatSocketService {
    private var socket: WebSocketSession? = null
    override suspend fun initSession(userInfo: UserInfo): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("$WEB_SOCKET_URL?login=${userInfo.login}&password=${userInfo.password}")
            }
            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Connection failed")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            //why should we use frame.text instead of just send("string") ?
            // because frame distinguishes types and it's convenient
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun observeMessages(): Flow<Message> {
        return try {
            //  incoming is ReceiveChannel of Frame. What is Frame
            socket?.incoming?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as Frame.Text).readText() ?: ""
                    val dto = Json.decodeFromString<MessageDto>(json)
                    mapper.mapDtoToEntity(dto)
                } ?: flow { }
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }

    }

    override suspend fun closeSession() {
        client.close()
    }
}