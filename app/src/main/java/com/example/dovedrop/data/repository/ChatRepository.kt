package com.example.dovedrop.data.repository

import com.example.dovedrop.domain.model.Message
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.request.RpcRequestBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult

interface ChatRepository {
    suspend fun getChatMessages(): List<Message>
    suspend fun getMessageOfA(): List<Message>
}

class ChatRepositoryImpl(private val supabase: SupabaseClient): ChatRepository {

    override suspend fun getChatMessages(): List<Message> {
        return supabase.from("messages")
            .select()
            .decodeList<Message>()
    }

    override suspend fun getMessageOfA(): List<Message> {
        return emptyList()
    }
}