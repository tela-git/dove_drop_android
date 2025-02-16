package com.example.dovedrop.chat.data.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestData(
    val email: String = "",
    val password: String = ""
)
