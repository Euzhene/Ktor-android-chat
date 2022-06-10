package com.euzhene.ktorandroidchat.domain.usecase

import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMessagesUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke(): Flow<Message> {
        return repository.observeMessages()
    }
}