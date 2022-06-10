package com.euzhene.ktorandroidchat.domain.usecase

import javax.inject.Inject

data class ChatUserUseCases @Inject constructor(
    val getUserInfoUseCase: GetUserInfoUseCase,
    val registerUserUseCase: RegisterUserUseCase
)
