package com.example.dovedrop.chat.data.repository

import com.example.dovedrop.chat.domain.model.ChatRoom
import com.example.dovedrop.chat.domain.network.ChatRepository
import com.example.dovedrop.chat.domain.util.NetworkError
import com.example.dovedrop.chat.domain.util.Result
import com.google.firebase.Firebase


class RemoteChatRepository(
    private val firebaseDb: Firebase
): ChatRepository {
    override suspend fun getChats(): Result<List<ChatRoom>, NetworkError> {
        TODO("Not yet implemented")
    }
}