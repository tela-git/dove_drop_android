package com.example.dovedrop.chat.domain.util

enum class NetworkError: Error {
    NO_INTERNET,
    REQUEST_TIMEOUT,
    SERIALIZATION_ERROR,
    UNKNOWN_ERROR
}