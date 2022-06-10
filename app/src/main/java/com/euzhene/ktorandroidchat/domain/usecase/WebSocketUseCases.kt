package com.euzhene.ktorandroidchat.domain.usecase

import javax.inject.Inject

data class WebSocketUseCases @Inject constructor(
    val initSessionUseCase: InitSessionUseCase,
    val sendMessageUseCase: SendMessageUseCase,
    val observeMessagesUseCase: ObserveMessagesUseCase,
    val closeSessionUseCase: CloseSessionUseCase
)
