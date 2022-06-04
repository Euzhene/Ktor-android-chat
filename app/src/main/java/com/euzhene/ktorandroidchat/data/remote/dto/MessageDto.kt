package com.euzhene.ktorandroidchat.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id:String,
    val text:String,
    val username:String,
    val timestamp:Long,
)
