package com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.domain.network.ChatRepository
import com.example.dovedrop.chat.domain.util.Result
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

    fun getAllMessages(chatRoomId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when(val response = chatRepository.getAllMessages(chatRoomId)) {
                is Result.Success -> {
                    _uiState.update { state->
                        state.copy(
                            isLoading = false,
                            chatMessages = response.data
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onSend(chatRoomId: String) {
        viewModelScope.launch {
            val result = chatRepository.chat(chatRoomId, uiState.value.text)
            when(result) {
                is Result.Error -> {

                }
                is Result.Success -> {
                    result.data.collect { chatMessage ->
                        if(chatMessage != null) {
                            Log.d("AuthTag", "Message received in viewmodel: ${chatMessage.text}")
                            _uiState.update { state ->
                                state.copy(
                                    chatMessages = uiState.value.chatMessages + chatMessage
                                )
                            }
                        }
                    }
                }
            }
        }
        _uiState.update { it.copy(text = "") }
    }

}

data class ChatRoomUIState(
    val chatMessages: List<ChatMessage> = listOf(),
    val isLoading : Boolean = false,
    val text: String = "",
)