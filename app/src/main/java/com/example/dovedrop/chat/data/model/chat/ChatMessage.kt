package com.example.dovedrop.chat.data.model.chat

import java.util.Date


data class ChatMessage(
    val id: String,
    val chatRoomId: String,
    val text: String,
    val sender: String,
    val status: MessageStatus,
    val timestamp: Long
)