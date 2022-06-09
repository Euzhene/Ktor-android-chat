package com.euzhene.ktorandroidchat

private const val BASE_REQUEST_URL ="http://192.168.31.152:8080"
private const val BASE_SOCKET_URL = "ws://192.168.31.152:8080"

const val USER_INFO_URL = "$BASE_REQUEST_URL/myInfo"
const val MESSAGES_URL = "${BASE_REQUEST_URL}/messages"
const val WEB_SOCKET_URL = "$BASE_SOCKET_URL/chat-socket"