package com.example.dovedrop.chat.data.model.chat

import com.example.dovedrop.chat.domain.util.Error

enum class ChatError : Error{
    SERVER_ERROR,
    UNKNOWN_ERROR,
    SERIALIZATION_ERROR,
}