package com.example.dovedrop.chat.domain.model

import com.squareup.moshi.Json


data class MessageDTO(
    val id: String,
    @Json(name = "sender_id") val senderId: String,
    @Json(name = "receiver_id") val receiverId: String,
    @Json(name = "created_at") val time: String,
    @Json(name = "message_body") val message: String
)

