package com.example.dovedrop.chat.data.model.auth

import com.example.dovedrop.chat.domain.util.Error

sealed class ResetPasswordError(val name: String) : Error {
    data object InvalidOTP: ResetPasswordError("INVALID_OTP")
    data object UnknownError: ResetPasswordError("UNKNOWN_ERROR")
    data object InvalidRequestFormat: ResetPasswordError("INVALID_REQUEST_FORMAT")
    data object ServerError: ResetPasswordError("SERVER_ERROR")
}
