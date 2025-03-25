package com.example.dovedrop.chat.domain.network

import com.example.dovedrop.chat.data.model.chat.ChatError
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.domain.util.GetAllChatsError
import com.example.dovedrop.chat.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getAllChatRooms(): Result<List<ChatRoom>?, GetAllChatsError>
    suspend fun chat(chatRoomId: String, message: String): Result<Flow<ChatMessage?>, ChatError>
    suspend fun getAllMessages(chatRoomId: String):  Result<List<ChatMessage>, ChatError>
}