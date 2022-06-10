package com.euzhene.ktorandroidchat.domain.usecase

import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.domain.repository.MainRepository
import com.euzhene.ktorandroidchat.utils.Resource
import javax.inject.Inject

class InitSessionUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(userInfo: UserInfo): Resource<Unit> {
        return repository.initSession(userInfo)
    }
}