package com.example.dovedrop.chat.presentation.ui.screens.auth

import com.example.dovedrop.chat.domain.util.AuthenticationError

sealed interface AuthEvents {
    data class Success(val success: String): AuthEvents
    data class Error(val error: AuthenticationError): AuthEvents
}