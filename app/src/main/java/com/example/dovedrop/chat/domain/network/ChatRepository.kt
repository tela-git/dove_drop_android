package com.example.dovedrop.chat.domain.network

import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.domain.util.GetAllChatsError
import com.example.dovedrop.chat.domain.util.Result

interface ChatRepository {
    suspend fun getAllChatRooms(): Result<List<ChatRoom>?, GetAllChatsError>
    suspend fun chat(chatRoomId: String, message: String): String
}