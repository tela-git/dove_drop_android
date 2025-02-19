package com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_detail

sealed interface ChatEvents {
    data object UnknownErrorOccurred: ChatEvents
    data object ErrorSendingMessage: ChatEvents
    data object MessageSentSuccessfully: ChatEvents
    data object ErrorUpdatingChats: ChatEvents
    data object ErrorAddingChatRoom: ChatEvents
}