package com.euzhene.ktorandroidchat.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val login:String,
    val password:String,
    val username:String? =null
)
