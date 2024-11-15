package com.example.dovedrop.chat.data.repository

import com.example.dovedrop.chat.domain.model.MessageDTO
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

interface ChatRepository {
    suspend fun getChatMessages(): List<MessageDTO>
    suspend fun getMessageOfA(): List<MessageDTO>
}

class ChatRepositoryImpl(private val supabase: SupabaseClient): ChatRepository {

    override suspend fun getChatMessages(): List<MessageDTO> {
        return supabase.from("messages")
            .select()
            .decodeList<MessageDTO>()
    }

    override suspend fun getMessageOfA(): List<MessageDTO> {
        return emptyList()
    }
}