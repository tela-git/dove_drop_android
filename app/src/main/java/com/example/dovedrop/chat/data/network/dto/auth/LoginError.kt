package com.example.dovedrop.chat.data.network.dto.auth

import com.example.dovedrop.chat.domain.util.Error

enum class LoginError: Error {
    INVALID_CRED_ERROR,
    EMAIL_VERIFY_ERROR,
    UNKNOWN_ERROR,
    TOKEN_ERROR
}
