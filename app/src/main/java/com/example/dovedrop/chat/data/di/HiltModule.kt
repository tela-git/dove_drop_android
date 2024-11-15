package com.example.dovedrop.chat.data.di

import com.example.dovedrop.BuildConfig
import com.example.dovedrop.chat.data.repository.ChatRepository
import com.example.dovedrop.chat.data.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.MoshiSerializer

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideSupabase() : SupabaseClient {
        val key = BuildConfig.API_KEY
        val url = BuildConfig.PROJECT_URL

        val supabase =  createSupabaseClient(
            supabaseKey = key,
            supabaseUrl = url
        ) {
            defaultSerializer = MoshiSerializer()
            install(Postgrest)
            install(Auth)
            install(Realtime)
        }
        return supabase
    }

    @Provides
    fun provideChatRepository(
        supabaseClient: SupabaseClient
    ): ChatRepository {
        return ChatRepositoryImpl(supabase = supabaseClient)
    }
}