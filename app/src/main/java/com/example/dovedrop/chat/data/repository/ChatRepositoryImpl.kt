package com.example.dovedrop.chat.data.repository

import android.util.Log
import com.example.dovedrop.chat.data.di.baseUrl
import com.example.dovedrop.chat.data.di.baseUrlWS
import com.example.dovedrop.chat.data.model.chat.ChatError
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.data.network.dto.auth.response.ApiResponse
import com.example.dovedrop.chat.domain.network.ChatRepository
import com.example.dovedrop.chat.domain.util.GetAllChatsError
import com.example.dovedrop.chat.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

private const val AUTH_TAG = "AuthTag"

class ChatRepositoryImpl(
    private val httpClient: HttpClient,
    private val encryptedPrefs: EncryptedPrefs
): ChatRepository {
    override suspend fun getAllChatRooms() : Result<List<ChatRoom>?, GetAllChatsError> {
        return try {
            val response = httpClient
                .get("$baseUrl/user/getAllChatRooms") {
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

    override suspend fun chat(chatRoomId: String, message: String): Result<Flow<ChatMessage?>, ChatError> {
        return try {
            val url = buildString {
                append(baseUrlWS)
                append("/chat/room")
                append("?chatRoomId=$chatRoomId")
                append("&timeStamp=${System.currentTimeMillis()}")
            }

            Log.d("AuthTag", "Connecting to WebSocket: $url")
            var chatMessage: ChatMessage? = null
            httpClient.webSocket(
                urlString = url,
                request = {
                    header(HttpHeaders.Authorization, "Bearer ${encryptedPrefs.getToken()}")
                }
            ) {
                Log.d("AuthTag", "WebSocket connection established")

                Log.d("AuthTag", "Sending message: $message")
                send(Frame.Text(message))
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    val string = frame.readText()
                    chatMessage = Json.decodeFromString<ChatMessage>(string)
                    Log.d("AuthTag", "Received message: $string")
                }
            }
            return Result.Success(flowOf(chatMessage))
        } catch (e: SerializationException) {
            Log.d("AuthTag", "Error in chat: ${e.message}")
            Result.Error(ChatError.SERIALIZATION_ERROR)
        }
        catch (e: Exception) {
            Log.d("AuthTag", "Error in chat: ${e.message}")
            Result.Error(ChatError.UNKNOWN_ERROR)
        }
    }

    override suspend fun getAllMessages(chatRoomId: String): Result<List<ChatMessage>, ChatError> {
        val url = buildString {
            append(baseUrl)
            append("/chat/getAllMessages")
            append("?chatRoomId=$chatRoomId")
        }
        return try {
            val response = httpClient
                .get(url) {
                    url {
                        header(HttpHeaders.Authorization, "Bearer ${encryptedPrefs.getToken()}")
                    }
                }
                .body<ApiResponse<List<ChatMessage>>>()
            return if(response.status == "Success") {
                Result.Success(response.data ?: emptyList())
            } else {
                return when(response.message) {
                    ChatError.SERVER_ERROR.name -> Result.Error(ChatError.SERVER_ERROR)
                    else -> Result.Error(ChatError.UNKNOWN_ERROR)
                }
            }

        } catch (e: SerializationException) {
            Log.d("AuthTag", "Error getting messages: ${e.message}")
            Result.Error(ChatError.SERIALIZATION_ERROR)
        }
        catch (e: Exception) {
            Log.d("AuthTag", "Error getting messages: ${e.message}")
            Result.Error(ChatError.UNKNOWN_ERROR)
        }
    }
}