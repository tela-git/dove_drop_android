package com.example.dovedrop.chat.presentation.ui.screens.contacts

sealed interface ContactEvents {
    data object ContactAddedSuccessfully: ContactEvents
    data object ContactAlreadyExists :ContactEvents
    data object FailedToAddContact: ContactEvents
}