package com.example.dovedrop.chat.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val id: String,
    val chatRoomId: String,
    val text: String,
    val sender: String,
    val status: String,
    val timeStamp: Long
)