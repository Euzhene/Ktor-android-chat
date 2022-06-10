package com.euzhene.ktorandroidchat.presentation.chat

import com.euzhene.ktorandroidchat.domain.model.Message

data class ChatState(
    val messages:List<Message> = emptyList(),
    val isLoading:Boolean = false
)
