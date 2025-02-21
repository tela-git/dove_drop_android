package com.example.dovedrop.chat.data.model.user

import com.example.dovedrop.chat.domain.util.Error

enum class GetContactsError: Error {
    UNAUTHORIZED,
    SERVER_ERROR,
    UNKNOWN_ERROR
}