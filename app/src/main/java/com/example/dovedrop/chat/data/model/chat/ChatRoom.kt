package com.example.dovedrop.chat.data.model.chat

import java.util.Date

data class ChatRoom(
    val id: String,
    val participants: List<String>,
    val type: ChatRoomType,
    val createdAt: Long,
    val lastMessage: ChatMessage?,
    val participant2Name: String,
    val participant2Dp: String,
)
