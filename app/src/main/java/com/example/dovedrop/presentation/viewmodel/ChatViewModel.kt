package com.example.dovedrop.presentation.viewmodel

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovedrop.data.repository.ChatRepository
import com.example.dovedrop.domain.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {
    private val _chatListUiState = MutableStateFlow(MessageUiState())
    val chatListUiState = _chatListUiState.asStateFlow()

    fun getChatList() {
        Log.d("LETTER", "getChatList() is called in viewmodel")
        viewModelScope.launch {
            try {
                val list = chatRepository.getChatMessages().map {
                    UiMessage(
                        id = it.id,
                        senderId = it.senderId,
                        receiverId = it.receiverId,
                        creationTime = it.time,
                        message = it.message
                    )
                }
                _chatListUiState.update { state ->
                    state.copy(
                        messagesList = list
                    )
                }
                Log.d("LETTER", "${list}")
            } catch (e: Exception) {
                Log.e("LETTER", "${e.message}")
            }
        }
    }
}

data class MessageUiState(
    val messagesList: List<UiMessage> = emptyList()
)
data class UiMessage(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val message: String,
    val creationTime: String
)