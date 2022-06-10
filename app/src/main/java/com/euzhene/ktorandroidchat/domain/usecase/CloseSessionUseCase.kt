package com.euzhene.ktorandroidchat.domain.usecase

import com.euzhene.ktorandroidchat.domain.repository.MainRepository
import javax.inject.Inject

class CloseSessionUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke() {
        return repository.closeSession()
    }
}