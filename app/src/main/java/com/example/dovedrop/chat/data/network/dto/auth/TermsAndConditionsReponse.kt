package com.example.dovedrop.chat.data.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TermsAndConditionsResponse(
    val termsAndConditions: String
)