package com.example.dovedrop.chat.presentation.ui.screens.main.contacts

sealed interface ContactEvents {
    data object ContactAddedSuccessfully: ContactEvents
    data object ContactAlreadyExists : ContactEvents
    data object FailedToAddContact: ContactEvents
}