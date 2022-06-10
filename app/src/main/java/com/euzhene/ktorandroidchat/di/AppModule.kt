package com.euzhene.ktorandroidchat.di

import com.euzhene.ktorandroidchat.data.mapper.ChatMapper
import com.euzhene.ktorandroidchat.data.remote.*
import com.euzhene.ktorandroidchat.data.repository.MainRepositoryImpl
import com.euzhene.ktorandroidchat.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClient(CIO) { //we use cio, not android, because the android engine doesn't support websockets
            install(Logging)
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

    @Provides
    @Singleton
    fun provideMessageService(client: HttpClient, mapper: ChatMapper): MessageService =
        MessageServiceImpl(client, mapper)

    @Provides
    @Singleton
    fun provideChatSocketService(client: HttpClient, mapper: ChatMapper): ChatSocketService =
        ChatSocketServiceImpl(client, mapper)

    @Provides
    @Singleton
    fun provideChatUserService(client: HttpClient): ChatUserService = ChatUserServiceImpl(client)

    @Provides
    @Singleton
    fun provideMainRepository(impl: MainRepositoryImpl): MainRepository = impl
}