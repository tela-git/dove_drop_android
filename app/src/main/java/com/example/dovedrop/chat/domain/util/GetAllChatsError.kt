package com.example.dovedrop.chat.domain.util

enum class GetAllChatsError: Error {
    SERVER_ERROR,
    UNKNOWN_ERROR,
    UNAUTHORIZED
}