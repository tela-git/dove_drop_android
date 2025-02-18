package com.example.dovedrop.chat.data.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class SimpleAPIResponse(
    val status: String,
    val message: String
)
