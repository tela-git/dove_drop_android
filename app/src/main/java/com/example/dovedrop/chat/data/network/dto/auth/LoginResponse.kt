package com.example.dovedrop.chat.data.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse<T>(
    val status: String,
    val message: String,
    val data: T?
)
