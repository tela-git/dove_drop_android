package com.example.dovedrop.chat.presentation.ui.screens.main.chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.qualifier._q

class ChatViewModel: ViewModel() {
    private val _chatHomeUIState = MutableStateFlow(ChatHomeUIState())
    val chatHomeUIState = _chatHomeUIState.asStateFlow()


    //Methods for updating ChatHomeScreen UI
    fun updateMoreOptionsVisibility() {
        _chatHomeUIState.update { it.copy(isMoreOptionsVisibility = !chatHomeUIState.value.isMoreOptionsVisibility) }
    }
}

data class ChatHomeUIState(
    val isLoading: Boolean = false,
    val isMoreOptionsVisibility: Boolean = false,
)