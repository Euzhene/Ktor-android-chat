package com.euzhene.ktorandroidchat.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.domain.usecase.GetAllMessagesUseCase
import com.euzhene.ktorandroidchat.domain.usecase.WebSocketUseCases
import com.euzhene.ktorandroidchat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val webSocketUseCases: WebSocketUseCases,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _messageText = mutableStateOf("")
    val messageText: State<String> = _messageText

    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun connectToChat() {
        getAllMessages()
        connectToSocket()
    }

    fun onMessageChange(message: String) {
        _messageText.value = message
    }

    private fun observeMessages() {
        webSocketUseCases.observeMessagesUseCase().onEach {
            val newMessageList = state.value.messages.toMutableList().apply {
                add(0, it)
            }
            _state.value = _state.value.copy(messages = newMessageList)
        }.launchIn(viewModelScope)
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val login = savedStateHandle.get<String>("login")
            val password = savedStateHandle.get<String>("password")
            if (login != null && password != null) {
                when (val result = getAllMessagesUseCase(UserInfo(login, password))) {
                    is Resource.Success -> _state.value =
                        ChatState(result.data ?: emptyList(), false)
                    is Resource.Error -> _toastEvent.emit(result.message ?: "Unknown error")
                }
            }
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (_messageText.value.isNotBlank()) {
                webSocketUseCases.sendMessageUseCase(_messageText.value)
            }
        }
    }

    private fun connectToSocket() {
        val login = savedStateHandle.get<String>("login")
        val password = savedStateHandle.get<String>("password")
        if (login != null && password != null) {
            viewModelScope.launch {
                when (val result = webSocketUseCases.initSessionUseCase(UserInfo(login, password))) {
                    is Resource.Success -> observeMessages()
                    is Resource.Error -> _toastEvent.emit(result.message ?: "Unknown error")
                }
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            webSocketUseCases.closeSessionUseCase()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}