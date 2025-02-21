package com.example.dovedrop.chat.data.model.user

import com.example.dovedrop.chat.domain.util.Error

enum class AddContactError : Error{
    CONTACT_ALREADY_EXISTS,
    UNKNOWN_ERROR,
    SERVER_ERROR,
    UNAUTHORIZED,
    INVALID_DATA_FORMAT
}