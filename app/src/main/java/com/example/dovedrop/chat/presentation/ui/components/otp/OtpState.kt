package com.example.dovedrop.chat.presentation.ui.components.otp

data class OtpState(
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val isCompleted: Boolean? = null
)