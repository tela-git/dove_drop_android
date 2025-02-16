package com.example.dovedrop.chat.data.network.dto.auth

import com.example.dovedrop.chat.domain.util.Error

sealed class SignUpError(val value: String): Error {
    data object UserAlreadyExists : SignUpError("USER_ALREADY_EXISTS")
    data object ErrorSendingOTP: SignUpError("ERROR_SENDING_OTP")
    data object UnknownError: SignUpError("UNKNOWN_ERROR")
    data object InvalidCredFormat: SignUpError("INVALID_CRED_FORMAT")
}