package com.example.dovedrop.chat.domain.model

import com.squareup.moshi.Json

data class ContactDTO(
    val id: String,
    @Json(name = "contact_holder_id") val contactHolderId: String,
    @Json(name = "contactee_id") val contacteeId: String?,
    val name: String,
    val email: String
)
