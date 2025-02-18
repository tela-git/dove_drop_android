package com.example.dovedrop.chat.data.model

import com.example.dovedrop.chat.domain.util.Error

sealed class VerifyEmailError(val value: String) : Error{
    data object InvalidOTP: VerifyEmailError("INVALID_OTP")
    data object ServerError: VerifyEmailError("SERVER_ERROR")
    data object BadRequest: VerifyEmailError("INVALID_DATA_FORMAT")
    data object UnknownError: VerifyEmailError("UNKNOWN_ERROR")
    data object NoOTPToVerify: VerifyEmailError("NO_OTP_TO_VERIFY")
}