package com.example.dovedrop.chat.data.model.auth

import com.example.dovedrop.chat.domain.util.Error

sealed class FPResponseError(val value: String ) : Error {
    data object InvalidRequestFormat: FPResponseError("INVALID_REQUEST_FORMAT")
    data object InvalidRequest: FPResponseError("INVALID_REQUEST")
    data object ServerError: FPResponseError("SERVER_ERROR")
    data object UnknownError: FPResponseError("UNKNOWN_ERROR")
}