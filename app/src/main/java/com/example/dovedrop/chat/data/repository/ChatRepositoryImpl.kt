package com.example.dovedrop.chat.data.repository

import android.util.Log
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.data.network.dto.auth.response.ApiResponse
import com.example.dovedrop.chat.domain.network.ChatRepository
import com.example.dovedrop.chat.domain.util.GetAllChatsError
import com.example.dovedrop.chat.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class ChatRepositoryImpl(
    private val httpClient: HttpClient,
    private val encryptedPrefs: EncryptedPrefs
): ChatRepository {
    override suspend fun getAllChatRooms() : Result<List<ChatRoom>?, GetAllChatsError> {

        return try {
            val response = httpClient
                .get("/user/getAllChatRooms") {
                url {
                    header(HttpHeaders.Authorization, "Bearer "+encryptedPrefs.getToken())
                }
            }.body<ApiResponse<List<ChatRoom>?>>()

            if(response.status == "Success") {
                Log.d("AuthTag", "status: ${response.status} and message: ${response.message} ")
                return Result.Success(response.data)
            } else {
                Log.d("AuthTag", "status: ${response.status} and message: ${response.message} ")
                return when(response.message) {
                    GetAllChatsError.UNAUTHORIZED.name -> {
                        Result.Error(GetAllChatsError.UNAUTHORIZED)
                    }
                    GetAllChatsError.SERVER_ERROR.name -> {
                        Result.Error(GetAllChatsError.SERVER_ERROR)
                    }
                    else -> {
                        Result.Error(GetAllChatsError.UNKNOWN_ERROR)
                    }
                }
            }
        } catch (e: Exception) {
            Result.Error(GetAllChatsError.UNKNOWN_ERROR)
        }
    }
}