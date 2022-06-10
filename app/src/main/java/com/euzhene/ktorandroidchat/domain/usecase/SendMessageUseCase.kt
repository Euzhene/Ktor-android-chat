package com.euzhene.ktorandroidchat.domain.usecase

import com.euzhene.ktorandroidchat.domain.repository.MainRepository
import com.euzhene.ktorandroidchat.utils.Resource
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(message:String): Resource<Unit> {
        return repository.sendMessage(message)
    }
}