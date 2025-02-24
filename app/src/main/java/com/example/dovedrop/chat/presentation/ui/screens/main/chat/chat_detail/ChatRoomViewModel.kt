package com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.domain.network.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatRoomViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatRoomUIState())
    val uiState = _uiState.asStateFlow()

    //Functions for updating UI
    fun updateInputMessage(text: String) {
        _uiState.update { it.copy(text = text) }
    }

    fun onSend(chatRoomId: String) {
        viewModelScope.launch {
            val result = chatRepository.chat(chatRoomId, uiState.value.text)
            Log.d("AuthTag", result)
        }
        _uiState.update { it.copy(text = "") }
    }

}

data class ChatRoomUIState(
    val chatMessages: List<ChatMessage> = emptyList(),
    val isLoading : Boolean = false,
    val text: String = "",
)