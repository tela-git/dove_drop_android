package com.example.dovedrop.chat.data.model.chat

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ChatRoom(
    val id: String,
    val participants: List<String>,
    val chatRoomType: String,
    val createdAt: Long,
    val lastMessage: ChatMessage? = null,
    val participant2Name: String,
    val participant2Dp: String,
)
