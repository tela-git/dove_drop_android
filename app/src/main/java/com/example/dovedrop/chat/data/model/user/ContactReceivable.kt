package com.example.dovedrop.chat.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class ContactDTO(
    val p2Email: String,
    val p2Name: String,
    val p2DpUrl: String,
    val hasAccount: Boolean,
    val availability: String,
)
