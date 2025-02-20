package com.example.dovedrop.chat.data.model.chat

sealed class MessageStatus(val name: String) {
    data object None: MessageStatus("NONE")
    data object Uploaded: MessageStatus("UPLOADED")
    data object Undelivered: MessageStatus("UNDELIVERED")
    data object Delivered: MessageStatus("DELIVERED")
    data object Read: MessageStatus("READ")
}