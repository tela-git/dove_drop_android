package com.example.dovedrop.chat.presentation.ui.screens.main.chat

import androidx.lifecycle.ViewModel
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.data.model.chat.ChatRoomType
import com.example.dovedrop.chat.data.model.chat.MessageStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.qualifier._q
import java.util.Date

class ChatViewModel: ViewModel() {
    private val _chatHomeUIState = MutableStateFlow(ChatHomeUIState())
    val chatHomeUIState = _chatHomeUIState.asStateFlow()

    fun getAllChatRooms() {
        _chatHomeUIState.update { state->
            state.copy(
                chatRooms = dummyChatRooms
            )
        }
    }
    //Methods for updating ChatHomeScreen UI
    fun updateMoreOptionsVisibility() {
        _chatHomeUIState.update { it.copy(isMoreOptionsVisibility = !chatHomeUIState.value.isMoreOptionsVisibility) }
    }
}

data class ChatHomeUIState(
    val isLoading: Boolean = false,
    val isMoreOptionsVisibility: Boolean = false,
    val chatRooms: List<ChatRoom>? = null
)

private val dummyChatRooms = listOf(
    ChatRoom(
        id = "chatRoomOne",
        participants = listOf("umesh1234@gmail.com", "decent-quail@gmail.com"),
        type = ChatRoomType.Private,
        createdAt = 1739440064019L,
        lastMessage = ChatMessage(
            id = "chatMessageOne",
            sender = "decent-quail@gmail.com",
            text = "Hey! How's it going?",
            timestamp = 1739688184000L,
            chatRoomId = "chatRoomOne",
            status = MessageStatus.Read
        ),
        participant2Name = "Decent",
        participant2Dp = "https://images.unsplash.com/photo-1544723795-3fb6469f5b39?q=80&w=1978&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    ChatRoom(
        id = "chatRoomTwo",
        participants = listOf("umesh1234@gmail.com", "hitanshexample@gmail.com"),
        type = ChatRoomType.Private,
        createdAt = 1739440064019L,
        lastMessage = ChatMessage(
            id = "messageTwo",
            sender = "umesh1234@gmail.com",
            text = "Let's meet at 5 PM.",
            timestamp = 1740033784000L,
            status = MessageStatus.Delivered,
            chatRoomId = "chatRoomTwo"
        ),
        participant2Name = "Hitansh",
        participant2Dp = "https://images.unsplash.com/photo-1505628346881-b72b27e84530?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzR8fHByb2ZpbGUlMjBwaWN0dXJlfGVufDB8fDB8fHww"
    ),
    ChatRoom(
        id = "chatRoomThree",
        participants = listOf("umesh1234@gmail.com", "madhavexample@gmail.com"),
        type = ChatRoomType.Private,
        createdAt = 1739440064019L,
        lastMessage = ChatMessage(
            id = "messageThree",
            sender = "umesh1234@gmail.com",
            text = "Welcome to the group!",
            timestamp = 1739947384000L,
            status = MessageStatus.Undelivered,
            chatRoomId = "chatRoomThree"
        ),
        participant2Dp = "sdf",
        participant2Name = "Madhav"
    )
)


