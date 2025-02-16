package com.example.dovedrop.chat.data.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestData(
    val name: String,
    val email: String,
    val password: String,
)
