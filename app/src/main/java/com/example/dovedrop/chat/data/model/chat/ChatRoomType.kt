package com.example.dovedrop.chat.data.model.chat

sealed class ChatRoomType(val name: String) {
    data object Private: ChatRoomType("PRIVATE")
    data object Group: ChatRoomType("GROUP")
}