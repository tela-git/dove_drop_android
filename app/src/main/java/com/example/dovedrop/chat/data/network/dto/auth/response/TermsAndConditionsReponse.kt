package com.example.dovedrop.chat.data.network.dto.auth.response

import kotlinx.serialization.Serializable

@Serializable
data class TermsAndConditionsResponse(
    val termsAndConditions: String
)