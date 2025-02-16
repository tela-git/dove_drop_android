package com.example.dovedrop.chat.domain.util

import android.content.Context
import com.example.dovedrop.R

enum class AuthenticationError: Error {
    INCORRECT_CREDENTIALS,
    NO_UPPERCASE,
    NO_LOWERCASE,
    NO_DIGIT,
    PASSWORD_TOO_SMALL
}

fun AuthenticationError.toString(context: Context): String {
    val resId =  when(this) {
        AuthenticationError.NO_DIGIT -> R.string.no_digit
        AuthenticationError.PASSWORD_TOO_SMALL -> R.string.small_password
        AuthenticationError.INCORRECT_CREDENTIALS -> R.string.incorrect_credentials
        AuthenticationError.NO_UPPERCASE -> R.string.no_uppercase
        AuthenticationError.NO_LOWERCASE -> R.string.no_lowercase
    }
    return context.getString(resId)
}